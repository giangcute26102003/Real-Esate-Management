package com.kot104.baitapbuoi13.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase

import com.kot104.baitapbuoi13.room.Entity.*
import com.kot104.baitapbuoi13.room.DAO.*
import com.kot104.baitapbuoi13.Converters



@Database(
    entities = [
    StudentEntity::class ,
    CustomerEntity::class,
    PropertyEntity::class,
    SellerEntity::class,
    CustomerRequirementsEntity::class,
    InteractionEntity::class
               ]
    ,version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class StudentsDB : RoomDatabase() {

    abstract fun studentDao(): StudentDAO
    abstract fun customerDao(): CustomerDAO
    abstract fun PropertyDao(): PropertyDAO
    abstract fun SellerDAO(): SellerDAO
    abstract fun CustomerRequirementDAO(): CustomerRequirementDAO
    abstract fun InteractionDAO(): InteractionDAO




    companion object {

        @Volatile
        private var INTANCE: StudentsDB? = null

        fun getIntance(context: Context): StudentsDB {
            synchronized(this){
                var intance = INTANCE
                if (intance == null){
                    intance = Room.databaseBuilder(
                        context.applicationContext,
                        StudentsDB::class.java,
                        "students_db19"
                    ) .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            db.execSQL("""
                               CREATE TRIGGER after_customer_insert
                            AFTER INSERT ON CustomerEntity
                            FOR EACH ROW
                            BEGIN
                                INSERT INTO CustomerRequirementsEntity (customerId, createdAt, updatedAt)
                                VALUES (NEW.customerId, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
                            END;
                            """.trimIndent())
                        }
                    }).build()
                }
                return intance
            }

        }

    }


}