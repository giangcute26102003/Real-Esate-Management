package com.kot104.baitapbuoi13

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kot104.baitapbuoi13.viewmodel.AuthState
import com.kot104.baitapbuoi13.viewmodel.AuthViewModel
import com.kot104.baitapbuoi13.viewmodel.SharedViewModel


@Composable
 fun profileScreen(navController : NavController , authViewModel: AuthViewModel) {
     val sharedViewModel: SharedViewModel = hiltViewModel()
    val seller by sharedViewModel.sellerViewModel.seller.collectAsState(initial = null)
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 10.dp)
            .background(color = Color(android.graphics.Color.parseColor("#FFFFFFFF"))),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout(
            Modifier
                .height(250.dp)
                .background(color = Color(android.graphics.Color.parseColor("#FFFFFFFF")))
        ) {
            val (topImg, profile, title, back, pen) = createRefs()

            Image(
                painterResource(id = R.drawable.card), null, Modifier
                .fillMaxWidth()
                .constrainAs(topImg) {
                    bottom.linkTo(parent.bottom)
                })
            Spacer(modifier = Modifier.height( 106.dp))
            Image(
                painterResource(id = R.drawable.user_2), null, Modifier
                .fillMaxWidth()
                .constrainAs(profile) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(topImg.bottom)
                })
            Text(text = "Profile",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 30.sp
                ),
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top, margin = 32.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            Image(
                painterResource(id = R.drawable.back), null, Modifier
                .constrainAs(back) {
                    top.linkTo(parent.top, margin = 24.dp)
                    start.linkTo(parent.start, margin = 24.dp)

                })
            Image(
                painterResource(id = R.drawable.write), null, Modifier
                .constrainAs(pen) {
                    top.linkTo(profile.top)
                    start.linkTo(profile.end)
                })
        }
        seller?.let {
            Text(
                text = it.name,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp),
                color = Color(android.graphics.Color.parseColor("#32357a"))
            )
        }
        seller?.let {
            Text(
                text = it.email,
                fontSize = 18.sp,
                color = Color(android.graphics.Color.parseColor("#747679"))
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 10.dp)
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.ic_4),
                    null,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { })
            }

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                TextButton(
                    onClick = {
                        navController.navigate(ROUTE_NAME_SCREEN.Backup.name)
                    }
                ) {Text(
                    text = "Backup Data",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                ) }

            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    null,
                    Modifier
                        .padding(end = 5.dp)
                        .clickable { })
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.btn_3),
                    null,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { })
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "Gallery",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    null,
                    Modifier
                        .padding(end = 5.dp)
                        .clickable { })
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.btn_4),
                    null,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { })
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "My Playlist",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    null,
                    Modifier
                        .padding(end = 5.dp)
                        .clickable { })
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.ic_7),
                    null,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { })
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "Change PassWords",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    null,
                    Modifier
                        .padding(end = 5.dp)
                        .clickable { })
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.btn_6),
                    null,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { })
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                TextButton(onClick = {

                    authViewModel.signout()
                    navController.popBackStack()
                    navController.navigate(ROUTE_NAME_SCREEN.LoginScreen.name)

                })
                {
                    Text(text = "Logout")
                }


            }

        }
    }
}