package com.kot104.baitapbuoi13

import android.annotation.SuppressLint
import androidx.annotation.FloatRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.Man
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kot104.baitapbuoi13.viewmodel.AuthState
import com.kot104.baitapbuoi13.viewmodel.AuthViewModel
import com.kot104.baitapbuoi13.viewmodel.SharedViewModel

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
//    val authViewModel: AuthViewModel = hiltViewModel()
//    val authState by authViewModel.authState.observeAsState()
//    LaunchedEffect(authState) {
//        if (authState !is AuthState.Authenticated) {
//            navController.navigate("LoginScreen")
//        }
//    }

    Scaffold(
        bottomBar = {
            val items = listOf(
                BottomNavigationItem(
                    title = "Home",
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                    hasNews = false,
                ),
                BottomNavigationItem(
                    title = "CustomerUI",
                    selectedIcon = Icons.Filled.Man,
                    unselectedIcon = Icons.Outlined.Man,
                    hasNews = false,
                    badgeCount = 45
                ),
                BottomNavigationItem(
                    title = "Property",
                    selectedIcon = Icons.Filled.HomeWork,
                    unselectedIcon = Icons.Outlined.HomeWork,
                    hasNews = true,
                ), BottomNavigationItem(
                    title = "Settings",
                    selectedIcon = Icons.Filled.Money,
                    unselectedIcon = Icons.Outlined.Money,
                    hasNews = true,
                )
            )
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                        },
                        label = {
                            Text(text = item.title)
                        },
                        alwaysShowLabel = false,
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (item.badgeCount != null) {
                                        Badge {
                                            Text(text = item.badgeCount.toString())
                                        }
                                    } else if (item.hasNews) {
                                        Badge()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), selectedItemIndex, navController)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int, navController: NavHostController) {
    val sharedViewModel: SharedViewModel = hiltViewModel()
    when (selectedIndex) {
        0 -> MainScreenContent(navController = navController, sharedViewModel = sharedViewModel)
        1 -> CustomerUi(navController, sharedViewModel.customerViewModel)
        2 -> PropertyUi(navController, sharedViewModel.propertyViewModel, 0)
        3-> profileScreen(navController, authViewModel = AuthViewModel())
    }
}

@Composable
fun MainScreenContent(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val scrollState = rememberScrollState()
    val seller by sharedViewModel.sellerViewModel.seller.collectAsState(initial = null)


    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        GreetingSection(sellerName = seller?.name ?: "")
        Spacer(modifier = Modifier.height(16.dp))
        WeatherSection()
        Spacer(modifier = Modifier.height(16.dp))
        BannerSliderSection()
        Spacer(modifier = Modifier.height(16.dp))
        NewsSection()
    }
}

@Composable
fun GreetingSection(sellerName: String) {
    Text(
        text = "Xin chào, $sellerName!",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun WeatherSection() {
    // Mocked data - Thay thế bằng dữ liệu thời tiết thực tế
    val temperature = "28°C"
    val condition = "Sunny"
    Text(
        text = " Thời tiết: $temperature, $condition",
        fontSize = 16.sp,
        color = Color.Gray,
        textAlign =  TextAlign.Left
    )
}


@Composable
fun BannerSliderSection() {
    val images = listOf(
        R.drawable.banner1,
        R.drawable.image2,
        R.drawable.image3
    )
    val pagerState = rememberPagerState(initialPage =0, pageCount = { 3 } )
    var currentPage by remember { mutableStateOf(0) }

    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
    }


    HorizontalPager(
        beyondViewportPageCount = images.size,
        state = pagerState,
        modifier = Modifier.height(200.dp)
    ) { page ->
        Image(
            painter = painterResource(id = images[page]),
            contentDescription = "Banner Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center
    ){
        images.forEachIndexed { index, _ ->
            val isCurrent = index == currentPage
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(if(isCurrent) 10.dp else 8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if(isCurrent) Color.Black else Color.Gray)
            )
        }
    }
}

@Composable
fun NewsSection() {
    val newsList = listOf(
        NewsItem(
            image = R.drawable.image4,
            title = "Bất động sản tăng giá mạnh ở trung tâm",
            description = "Bất động sản ở các khu vực trung tâm đang ghi nhận mức tăng giá kỷ lục trong thời gian gần đây, nhiều nhà đầu tư quan tâm"
        ),
        NewsItem(
            image = R.drawable.image2,
            title = "Dự án căn hộ mới ra mắt tại khu đô thị phía đông",
            description = "Hàng loạt dự án căn hộ cao cấp mới được tung ra thị trường bất động sản phía đông thành phố, hứa hẹn là một điểm nóng đầu tư mới"
        ),
        NewsItem(
            image = R.drawable.image3,
            title = "Lãi suất giảm, thị trường bất động sản ấm dần lên",
            description = "Sau thời gian đóng băng, thị trường bất động sản có những dấu hiệu ấm lên nhờ vào việc giảm lãi suất của ngân hàng nhà nước"
        ),
    )

    Column{
        newsList.forEach { news ->
            NewsCard(newsItem = news)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun NewsCard(newsItem: NewsItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = newsItem.image),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = newsItem.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = newsItem.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

data class NewsItem(val image: Int, val title: String, val description: String)