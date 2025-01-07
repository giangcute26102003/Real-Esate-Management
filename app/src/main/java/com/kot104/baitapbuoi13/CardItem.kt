package com.kot104.baitapbuoi13
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.image1),  // Thay bằng ID hình ảnh của bạn
            contentDescription = "Property Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )


        Image(
            painter = painterResource(id = R.drawable.favorite),
            contentDescription = "Favorite Icon",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, bottom = 24.dp)
                .background(
                    color = colorResource(id = R.color.half_black),
                    shape = RoundedCornerShape(4.dp)
                )
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Image(
                painter = painterResource(id = R.drawable.location),
                contentDescription = "Location Icon",
                modifier = Modifier
                    .padding(start = 3.dp, bottom = 14.dp)
                    .size(16.dp)
                ,

                )

            Column(modifier = Modifier.padding(start = 8.dp).weight(1f)) {

                Text(
                    text = "141 Chien thang",
                    color = Color.White,
                    style = TextStyle(fontSize = 14.sp),
                )

                Text(
                    text = "7 tỷ",
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                )
            }


            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.size),
                    contentDescription = "Size Icon",
                    modifier = Modifier
                        .size(20.dp)
                    ,
                )
                Text(
                    text = "20 m2",
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(end = 16.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.bed),
                    contentDescription = "Bed Icon",
                    modifier = Modifier
                        .size(20.dp)
                    ,
                )
                Text(
                    text = "2",
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(end = 16.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.bath),
                    contentDescription = "Bath Icon",
                    modifier = Modifier
                        .size(20.dp)

                )
                Text(
                    text = "3",
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(end = 16.dp)
                )
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview1() {
    CardItem()
}