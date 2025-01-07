//package com.kot104.baitapbuoi13
//
//import androidx.compose.material3.Icon
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.res.painterResource
//import androidx.navigation.NavController
//
//@Composable
//fun BottomNavigationBar(navController: NavController) {
//    val items = listOf(
//        NavigationItem.Home,
//        NavigationItem.Search,
//        NavigationItem.Profile
//    )
//
//    NavigationBar {
//        items.forEach { item ->
//            NavigationBarItem(
//                selected = item.route == navController.currentDestination?.route,
//                onClick = {
//                    navController.navigate(item.route) {
//                        popUpTo(navController.graph.startDestinationId) { saveState = true }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                },
//                icon = {
//                    Icon(
//                        painter = painterResource(id = item.icon),
//                        contentDescription = item.title
//                    )
//                },
//                label = { Text(text = item.title) }
//            )
//        }
//    }
//}
//
//data class NavigationItem(val route: String, val title: String, val icon: Int)
//
//object NavigationItem {
//    val Home = NavigationItem("home", "Home", R.drawable.ic_home)
//    val Search = NavigationItem("search", "Search", R.drawable.ic_search)
//    val Profile = NavigationItem("profile", "Profile", R.drawable.ic_profile)
//}
