//package com.kot104.baitapbuoi13
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.BottomNavigation
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardElevation
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun NewsScreen() {
//    val newsList = listOf(
//        NewsItem(
//            title = "Tin tức 1",
//            content = "Nội dung tin tức 1",
//            imageResId = R.drawable.image1
//        ),
//        NewsItem(
//            title = "Tin tức 2",
//            content = "Nội dung tin tức 2",
//            imageResId = R.drawable.image2
//        ),
//        NewsItem(
//            title = "Tin tức 3",
//            content = "Nội dung tin tức 3",
//            imageResId = R.drawable.image3
//        ),
//        NewsItem(
//            title = "Tin tức 4",
//            content = "Nội dung tin tức 4",
//            imageResId = R.drawable.image4
//        ),
//        NewsItem(
//            title = "Tin tức 4",
//            content = "Nội dung tin tức 4",
//            imageResId = R.drawable.image4
//        ),
//        NewsItem(
//            title = "Tin tức 4",
//            content = "Nội dung tin tức 4",
//            imageResId = R.drawable.image4
//        ),
//        NewsItem(
//            title = "Tin tức 4",
//            content = "Nội dung tin tức 4dsfsssseeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
//            imageResId = R.drawable.image4
//        )
//    )
//
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("Trang Tin Tức") })
//        }
//
//    ) { innerPadding ->
//        LazyColumn(
//            modifier = Modifier
//                .padding(innerPadding) // Tự động áp dụng padding từ Scaffold
//                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 100.dp)
//        ) {
//            items(newsList) { newsItem ->
//                NewsCard(newsItem)
//                Spacer(modifier = Modifier.height(12.dp)) // Khoảng cách giữa các Card
//
//
//            }
//        }
//    }
//}
//
//@Composable
//fun NewsCard(newsItem: NewsItem) {
//    Card(modifier = Modifier
//        .fillMaxSize()
//        .wrapContentHeight()
//
//
//    ) {
//        Row(
//            modifier = Modifier.padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Image(
//                painter = painterResource(newsItem.imageResId),
//                contentDescription = newsItem.title,
//                modifier = Modifier
//                    .size(64.dp)
//                    .clip(RoundedCornerShape(8.dp))
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Column {
//                Text(text = newsItem.title, style = MaterialTheme.typography.titleLarge)
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = newsItem.content,
//                    style = MaterialTheme.typography.bodyLarge,
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis
//                )
//            }
//        }
//    }
//}
//
//data class NewsItem(
//    val title: String,
//    val content: String,
//    val imageResId: Int
//)
