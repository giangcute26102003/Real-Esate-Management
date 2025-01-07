package com.kot104.baitapbuoi13

import Register
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kot104.baitapbuoi13.viewmodel.SharedViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.kot104.baitapbuoi13.backupdata.Backup
//import com.kot104.baitapbuoi13.backupdata.Backup
import com.kot104.baitapbuoi13.viewmodel.AuthViewModel


enum class ROUTE_NAME_SCREEN {
    Main,
    Detail,
    IntroUI,
    LoginScreen,
    MainTest,
    CustomerUI,
    DetailCustomer,
    DashBoard,
    DetailProperty,
    PropertyUI,
    InteractionUI,
    Register,
    ProfileScreen,
    Backup,
}
@Composable
fun AppNavHost() {
    val sharedViewModel: SharedViewModel = hiltViewModel()
    val navController  = rememberNavController()
    val modifier = Modifier
    val authViewModel = AuthViewModel()


    NavHost(
        navController = navController,
        startDestination = ROUTE_NAME_SCREEN.IntroUI.name
    ) {
        composable(ROUTE_NAME_SCREEN.IntroUI.name) {
            introUI(navController)
        }

        composable(
            route = "${ROUTE_NAME_SCREEN.MainTest.name}/{customerId}/{name}/{phoneNumber}/{email}/{address}/{dob}"
        ) { backStackEntry ->
            MainTest(
                navController,
                sharedViewModel.customerViewModel,
                customerId = backStackEntry.arguments?.getString("customerId"),
                name = backStackEntry.arguments?.getString("name").toString(),
                phoneNumber = backStackEntry.arguments?.getString("phoneNumber")!!,
                email = backStackEntry.arguments?.getString("email")!!,
                 address = backStackEntry.arguments?.getString("address") ?: "Unknown",
                dob = backStackEntry.arguments?.getString("dob")!!,
            )
        }
        composable(ROUTE_NAME_SCREEN.LoginScreen.name) {
            LoginScreen(navController, authViewModel)
        }
        composable(ROUTE_NAME_SCREEN.DashBoard.name) {
            Dashboard(navController)
        }
        composable(ROUTE_NAME_SCREEN.Main.name) {
            MainScreen( navController )
        }
        composable(
            route = "${ROUTE_NAME_SCREEN.Detail.name}/{studentId}/{hoTen}/{mssv}/{diemTB}/{daRaTruong}"
        ) { backStackEntry ->
            DetailScreen(
                navController,
                viewModel = sharedViewModel.studentViewModel,
                studentId = backStackEntry.arguments?.getString("studentId"),
                hoTen = backStackEntry.arguments?.getString("hoTen")!!,
                mssv = backStackEntry.arguments?.getString("mssv")!!,
                diemTB = backStackEntry.arguments?.getString("diemTB")!!,
                daRaTruong = backStackEntry.arguments?.getString("daRaTruong")!!
            )
        }
        composable(ROUTE_NAME_SCREEN.CustomerUI.name) {
            CustomerUi(navController, sharedViewModel.customerViewModel)
        }
//        composable(
//            route = "${ROUTE_NAME_SCREEN.DetailCustomer.name}/{customerId}/{name}/{phoneNumber}/{email}/{address}/{dob}"
//        ) { backStackEntry ->
//            DetailCustomer(
//                navController,
//                sharedViewModel.customerViewModel,
//                customerId = backStackEntry.arguments?.getString("customerId"),
//                name = backStackEntry.arguments?.getString("name")!!,
//                phoneNumber = backStackEntry.arguments?.getString("phoneNumber")!!,
//                email = backStackEntry.arguments?.getString("email")!!,
//                address = backStackEntry.arguments!!.getString("address")!!,
//                dob = backStackEntry.arguments?.getString("dob")!!
//            )
//        }
        composable(ROUTE_NAME_SCREEN.PropertyUI.name) {
            val requirementId : Int = 0
            PropertyUi(navController, sharedViewModel.propertyViewModel, requirementId)
        }
        composable(route = "${ROUTE_NAME_SCREEN.PropertyUI.name}/{customerId}"){
            val customerId = it.arguments?.getString("customerId")?.toIntOrNull() ?: 0
            PropertyUi(navController = navController, sharedViewModel.propertyViewModel, customerId)
        }
        composable(
            route = "${ROUTE_NAME_SCREEN.DetailProperty.name}/{propertyId}/"
        ) { backStackEntry ->
            DetailProperty(
                navController,
                sharedViewModel.propertyViewModel,
                propertyId = backStackEntry.arguments?.getString("propertyId").toString()
//                addressProperty = backStackEntry.arguments?.getString("addressProperty")!!,
//                propertyType = backStackEntry.arguments?.getString("propertyType")!!,
//                imageUrl = backStackEntry.arguments?.getString("imageUrl")!!,
//                bathrooms = backStackEntry.arguments?.getString("bathrooms")!!,
//                bedrooms = backStackEntry.arguments?.getString("bedrooms")!!,
//                floor = backStackEntry.arguments?.getString("floor")!!,
//                size = backStackEntry.arguments?.getString("size")!!,
//                price = backStackEntry.arguments?.getString("price")!!,
//                description = backStackEntry.arguments?.getString("description")!!,
//                phoneOwner = backStackEntry.arguments?.getString("phoneOwner")!!,
//                legalDocuments = backStackEntry.arguments?.getString("legalDocuments")!!,
//                availability = backStackEntry.arguments?.getString("availability")!!
            )
        }
        composable(
            route = "${ROUTE_NAME_SCREEN.InteractionUI.name}/{propertyId}") {
                backStackEntry ->
            InteractionUI(navController, propertyId = backStackEntry.arguments?.getString("propertyId")!!.toInt() )
        }

        composable(ROUTE_NAME_SCREEN.Register.name) {
            Register(modifier,navController,authViewModel,sharedViewModel)
        }
        composable(ROUTE_NAME_SCREEN.ProfileScreen.name) {
            profileScreen(navController,authViewModel)
        }
        composable(ROUTE_NAME_SCREEN.Backup.name) {
            Backup()
        }
    }

}



