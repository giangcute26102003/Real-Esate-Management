package com.kot104.baitapbuoi13

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController


@Composable
fun introUI(navController : NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Image(
                painter = painterResource(id = R.drawable.intro_pic),
                contentDescription = "Intro Image",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Simplify Real Estate Data Management",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Text(
                text = "   An optimal solution that helps real estate professionals manage customer data efficiently and intelligently!   ",
                style = TextStyle(
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                SocialIconButton(iconId = R.drawable.facebook, modifier = Modifier.padding(end = 16.dp))
                SocialIconButton(iconId = R.drawable.google, modifier = Modifier.padding(horizontal = 16.dp))

                Button(
                    onClick = { navController.navigate(ROUTE_NAME_SCREEN.LoginScreen.name) },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                ) {
                    Text(
                        text = "Get Started",
                        color = colorResource(id = R.color.purple_200),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    )
                }


            }


        }
    }
}

@Composable
fun SocialIconButton(iconId: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(50.dp)
            .background(
                color = Color.DarkGray, // Giữ nền trong suốt
                shape = RoundedCornerShape(4.dp),
            )
            .border(
                width = 1.dp,
                color = Color.Transparent, // Màu viền
                shape = RoundedCornerShape(4.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = null // Không áp dụng bộ lọc màu để giữ nguyên màu gốc của icon
        )
    }
}



