package com.kot104.baitapbuoi13
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("SuspiciousIndentation")
@Composable
fun TestUI(name : String,dob : String , phone : String, email : String) {
    val context = LocalContext.current
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(186.dp)


        ) {
            Image(
                painter = painterResource(id = R.drawable.cardvisit3),
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop, // Điều chỉnh cách ảnh hiển thị (Crop, Fit, FillBounds, etc.)
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) {

                Text(
                    text = name,
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF000000) // Light blue color
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))

                ContactItem(
                    iconId = R.drawable.happybirthday, // Replace with your icon
                    text = dob,
                )
                Spacer(modifier = Modifier.height(8.dp))

                ContactItem(
                    iconId = R.drawable.phonecall, // Replace with your icon
                    text = phone,
                    isPhone = true

                )
                Spacer(modifier = Modifier.height(8.dp))

                    ContactItem(
                    iconId = R.drawable.gmail,  // Replace with your icon
                    text = email,
                )




            }
            Image(
                painter = painterResource(id = R.drawable.zalo_icon),
                contentDescription = "Zalo Icon",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .size(40.dp)
                    .clickable {

                        if (isZaloInstalled(context)) {
                            openZaloWithNumber(context, phone)
                        }
                    }
            )
        }
    }

@Composable
fun ContactItem(iconId: Int, text: String, isPhone: Boolean = false) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                if (isPhone) {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$text"))
                    context.startActivity(intent)
                }
            }
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = "Contact Icon",
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = TextStyle(fontSize = 16.sp)
        )
    }
}
private fun isZaloInstalled(context: Context): Boolean {
    return try {
        context.packageManager.getPackageInfo("com.zing.zalo", 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

private fun openZaloWithNumber(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://zalo.me/$phoneNumber"))
    try{
        context.startActivity(intent)
    }catch(e : Exception){
        Log.e("Zalo error", e.message ?: "")
    }

}