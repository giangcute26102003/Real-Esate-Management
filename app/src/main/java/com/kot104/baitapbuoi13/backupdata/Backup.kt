package com.kot104.baitapbuoi13.backupdata

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

@Composable
fun Backup() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val status = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Button(
            onClick = {
                scope.launch {
                    status.value = "Backing up..."
                    status.value = performBackup(context)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Backup Data")
        }

        Button(
            onClick = {
                scope.launch {
                    status.value = "Restoring..."
                    status.value = performRestore(context)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Restore Data")
        }

        Button(
            onClick = {
                scope.launch {
                    status.value = "Merging..."
                    status.value = performMerge(context)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Merge Data")
        }

        Text(
            text = status.value,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
private suspend fun performBackup(context: Context): String {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
        ?: return "You must be logged in to backup data"

    return try {
        val dbPath = context.getDatabasePath("students_db19").absolutePath
        val dbFile = File(dbPath)
        if (!dbFile.exists()) return "Database file not found"

        val backupDir = File(context.cacheDir, "backup").apply { mkdirs() }
        val encryptedFile = File(backupDir, "students_db19_encrypted.db")

        // Encrypt the database
        AESUtil.encryptFile(dbFile, encryptedFile)

        val zipFile = File(backupDir, "your_backup.zip")
        zipFile.createZip(listOf(encryptedFile))

        val storageRef = FirebaseStorage.getInstance().reference
            .child("backups/$userId/your_backup.zip")
        storageRef.putFile(Uri.fromFile(zipFile)).await()

        backupDir.deleteRecursively()
        "Backup completed successfully!"
    } catch (e: Exception) {
        Log.e("Backup", "Error during backup", e)
        "Backup failed: ${e.message}"
    }
}

private suspend fun performMerge(context: Context): String {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
        ?: return "You must be logged in to merge data"

    return try {
        Log.d("Merge", "Starting merge for user: $userId")

        val storageRef = FirebaseStorage.getInstance().reference
            .child("backups/$userId/your_backup.zip")


        val localFile = File(context.cacheDir, "your_backup.zip")
        storageRef.getFile(localFile).await()

        val restoreDir = File(context.cacheDir, "restore").apply { mkdirs() }
        localFile.unzip(restoreDir)

        val encryptedFile = File(restoreDir, "students_db19_encrypted.db")
        val decryptedFile = File(context.cacheDir, "students_db19_merged.db")

        // Giải mã cơ sở dữ liệu backup
        Log.d("Merge", "Decrypting database file")
        AESUtil.decryptFile(encryptedFile, decryptedFile)

        val dbPath = context.getDatabasePath("students_db19").absolutePath

        Log.d("Merge", "Opening databases")
        // Mở cơ sở dữ liệu hiện tại và cơ sở dữ liệu backup để merge
        SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE).use { currentDb ->
            SQLiteDatabase.openDatabase(decryptedFile.absolutePath, null, SQLiteDatabase.OPEN_READONLY).use { backupDb ->
                Log.d("Merge", "Starting transaction")
                currentDb.beginTransaction()
                try {
                    val tables = backupDb.rawQuery(
                        "SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'android_%' AND name NOT LIKE 'sqlite_%'", null
                    ).use { cursor ->
                        generateSequence { if (cursor.moveToNext()) cursor.getString(0) else null }.toList()
                    }

                    Log.d("Merge", "Merging tables")
                    tables.forEach { tableName ->
                        Log.d("Merge", "Merging table: $tableName")
                        backupDb.rawQuery("SELECT * FROM $tableName", null).use { cursor ->
                            while (cursor.moveToNext()) {
                                val values = android.content.ContentValues()
                                for (i in 0 until cursor.columnCount) {
                                    values.put(cursor.getColumnName(i), cursor.getString(i))
                                }
                                try{
                                    currentDb.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE)
                                } catch(e:Exception) {
                                    Log.e("Merge", "Error merging table $tableName", e)
                                }

                            }
                        }
                    }
                    Log.d("Merge", "Setting transaction successful")
                    currentDb.setTransactionSuccessful()
                } finally {
                    Log.d("Merge", "Ending transaction")
                    currentDb.endTransaction()
                }
            }
        }
        Log.d("Merge", "Deleting temp files and directories")
        // Xóa file tạm
        restoreDir.deleteRecursively()
        localFile.delete()
        decryptedFile.delete()
        Log.d("Merge", "Merge completed")
        "Merge completed successfully!"
    } catch (e: Exception) {
        Log.e("Merge", "Error during merge", e)
        "Merge failed: ${e.message}"
    }
}
private suspend fun performRestore(context: Context): String {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
        ?: return "You must be logged in to restore data"

    return try {
        val storageRef = FirebaseStorage.getInstance().reference
            .child("backups/$userId/your_backup.zip")

        val localFile = File(context.cacheDir, "your_backup.zip")
        storageRef.getFile(localFile).await()

        val restoreDir = File(context.cacheDir, "restore").apply { mkdirs() }
        localFile.unzip(restoreDir)

        val encryptedFile = File(restoreDir, "students_db19_encrypted.db")
        val decryptedFile = File(context.cacheDir, "students_db19.db")

        // Đảm bảo đóng cơ sở dữ liệu hiện tại trước khi restore
        context.databaseList().forEach { dbName ->
            if (dbName == "students_db19") {
                val dbPath = context.getDatabasePath(dbName)
                SQLiteDatabase.openDatabase(dbPath.absolutePath, null, SQLiteDatabase.OPEN_READWRITE).use { db ->
                    db.close()
                }
            }
        }

        // Giải mã cơ sở dữ liệu
        AESUtil.decryptFile(encryptedFile, decryptedFile)

        // Sao chép file cơ sở dữ liệu giải mã vào vị trí của ứng dụng
        decryptedFile.copyTo(File(context.getDatabasePath("students_db19").absolutePath), overwrite = true)

        restoreDir.deleteRecursively()
        localFile.delete()

        // Restart ứng dụng
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)

        "Restore completed! App will restart."
    } catch (e: Exception) {
        Log.e("Restore", "Error during restore", e)
        "Restore failed: ${e.message}"
    }
}


// Extension functions
fun File.createZip(files: List<File>) {
    FileOutputStream(this).use { fos ->
        ZipOutputStream(fos).use { zos ->
            files.forEach { file ->
                FileInputStream(file).use { fis ->
                    zos.putNextEntry(ZipEntry(file.name))
                    fis.copyTo(zos)
                    zos.closeEntry()
                }
            }
        }
    }
}

fun File.unzip(outputDir: File) {
    FileInputStream(this).use { fis ->
        ZipInputStream(fis).use { zis ->
            generateSequence { zis.nextEntry }.forEach { entry ->
                File(outputDir, entry.name).apply {
                    outputStream().use { zis.copyTo(it) }
                }
            }
        }
    }
}
