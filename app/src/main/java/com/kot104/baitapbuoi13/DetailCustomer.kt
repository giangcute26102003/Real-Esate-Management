//package com.kot104.baitapbuoi13
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.gestures.rememberScrollableState
//import androidx.compose.foundation.gestures.scrollable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.safeDrawingPadding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedIconButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.kot104.baitapbuoi13.room.Entity.CustomerEntity
//import com.kot104.baitapbuoi13.room.Entity.StudentEntity
//import com.kot104.baitapbuoi13.viewmodel.CustomerViewModel
//import com.kot104.baitapbuoi13.viewmodel.StudentViewModel
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//
//
//
//
//@Composable
//fun DetailCustomer(
//    navController: NavController,
//    viewModel: CustomerViewModel, // Đổi thành CustomerViewModel
//    customerId: String?,
//    name: String,
//    phoneNumber: String,
//    email: String,
//    address: String,
//    dob: String,
//) {
//    val showDialog = remember { mutableStateOf(false) }
//    val showUpdateDialog = remember { mutableStateOf(false) }
//    var inputName by remember { mutableStateOf(name) }
//    var inputPhoneNumber by remember { mutableStateOf(phoneNumber) }
//    var inputEmail by remember { mutableStateOf(email) }
//    var inputAddress by remember { mutableStateOf(address?: "address") }
//    var inputDob by remember { mutableStateOf(dob) }
//
//    val empty by remember { mutableStateOf("") }
//
//    // Các logic khác giữ nguyên, chỉ đổi `StudentEntity` thành `CustomerEntity`
//    Spacer(modifier = Modifier.height(30.dp))
//    WeatherSection()
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.fillMaxSize().safeDrawingPadding()
//    ) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(300.dp)
//                .padding(15.dp),
//            elevation = CardDefaults.cardElevation(4.dp)
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Bottom,
//                modifier = Modifier.fillMaxSize()
//                    .verticalScroll(rememberScrollState())
//            ) {
//                Spacer(modifier = Modifier.height(40.dp))
//                    Row {
//                        Text(
//                            text = "ID: ",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 24.sp,
//                            color = MaterialTheme.colorScheme.primary
//                        )
//                        Text(
//                            text = customerId ?: "", // Thay đổi từ studentId thành customerId
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 24.sp,
//                            color = MaterialTheme.colorScheme.primary
//                        )
//                    }
//                    Row {
//                        Text(
//                            text = "Name: ",
//                            fontSize = 16.sp,
//                        )
//                        Text(
//                            text = name,  // Thay đổi từ hoTen thành name
//                            fontSize = 16.sp,
//                        )
//                    }
//                    Row {
//                        Text(
//                            text = "Phone Number: ",
//                            fontSize = 16.sp,
//                        )
//                        Text(
//                            text = phoneNumber, // Thay đổi từ mssv thành phoneNumber
//                            fontSize = 16.sp,
//                        )
//                    }
//                    Row {
//                        Text(
//                            text = "Email: ",
//                            fontSize = 16.sp,
//                        )
//                        Text(
//                            text = email,  // Thay đổi từ diemTB thành email
//                            fontSize = 16.sp,
//                        )
//                    }
//                    Row {
//                        Text(
//                            text = "Address: ",
//                            fontSize = 16.sp,
//                        )
//                        Text(
//                            text = address, // Thay đổi từ daRaTruong thành address
//                            fontSize = 16.sp,
//                        )
//                    }
//                    Row {
//                        Text(
//                            text = "Date of Birth: ",
//                            fontSize = 16.sp,
//                        )
//                        Text(
//                            text = dob,  // Thay đổi từ diemTB thành dob
//                            fontSize = 16.sp,
//                        )
//                    }
//                Spacer(modifier = Modifier.height(30.dp))
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(1.dp)
//                        .background(MaterialTheme.colorScheme.background)
//                ) {
//
//                }
//                Row(
//                    horizontalArrangement = Arrangement.SpaceEvenly,
//                    verticalAlignment = Alignment.Bottom,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(10.dp)
//                ) {
//                    OutlinedIconButton(
//                        onClick = { showDialog.value = true },
//                        shape = RoundedCornerShape(50.dp),
//                        modifier = Modifier.size(height = 50.dp, width = 100.dp)
//                    ) {
//                        Row {
//                            Icon(
//                                painter = painterResource(id = R.drawable.ic_delete),
//                                contentDescription = null
//                            )
//                            Text(text = "Delete")
//                        }
//                    }
//                    OutlinedIconButton(
//                        onClick = {showUpdateDialog.value = true},
//                        shape = RoundedCornerShape(50.dp),
//                        modifier = Modifier.size(height = 50.dp, width = 100.dp)
//                    ) {
//                        Row {
//                            Icon(
//                                painter = painterResource(id = R.drawable.ic_edit),
//                                contentDescription = null
//                            )
//                            Text(text = "Update")
//                        }
//                    }
//                }
//            }
//        }
//    }
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(300.dp)
//            .padding(15.dp),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Bottom,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Spacer(modifier = Modifier.height(40.dp))
//            Row {
//                Text(
//                    text = "ID: ",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 24.sp,
//                    color = MaterialTheme.colorScheme.primary
//                )
//                Text(
//                    text = customerId ?: "", // Thay đổi từ studentId thành customerId
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 24.sp,
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
//            Row {
//                Text(
//                    text = "Name: ",
//                    fontSize = 16.sp,
//                )
//                Text(
//                    text = name,  // Thay đổi từ hoTen thành name
//                    fontSize = 16.sp,
//                )
//            }
//            Row {
//                Text(
//                    text = "Phone Number: ",
//                    fontSize = 16.sp,
//                )
//                Text(
//                    text = phoneNumber, // Thay đổi từ mssv thành phoneNumber
//                    fontSize = 16.sp,
//                )
//            }
//            Row {
//                Text(
//                    text = "Email: ",
//                    fontSize = 16.sp,
//                )
//                Text(
//                    text = email,  // Thay đổi từ diemTB thành email
//                    fontSize = 16.sp,
//                )
//            }
//            Row {
//                Text(
//                    text = "Address: ",
//                    fontSize = 16.sp,
//                )
//                Text(
//                    text = address, // Thay đổi từ daRaTruong thành address
//                    fontSize = 16.sp,
//                )
//            }
//            Row {
//                Text(
//                    text = "Date of Birth: ",
//                    fontSize = 16.sp,
//                )
//                Text(
//                    text = dob,  // Thay đổi từ diemTB thành dob
//                    fontSize = 16.sp,
//                )
//            }
//
//            Spacer(modifier = Modifier.height(30.dp))
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(1.dp)
//                    .background(MaterialTheme.colorScheme.background)
//            ) {
//
//            }
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                verticalAlignment = Alignment.Bottom,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(10.dp)
//            ) {
//                OutlinedIconButton(
//                    onClick = { showDialog.value = true },
//                    shape = RoundedCornerShape(50.dp),
//                    modifier = Modifier.size(height = 50.dp, width = 100.dp)
//                ) {
//                    Row {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_delete),
//                            contentDescription = null
//                        )
//                        Text(text = "Delete")
//                    }
//                }
//                OutlinedIconButton(
//                    onClick = {showUpdateDialog.value = true},
//                    shape = RoundedCornerShape(50.dp),
//                    modifier = Modifier.size(height = 50.dp, width = 100.dp)
//                ) {
//                    Row {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_edit),
//                            contentDescription = null
//                        )
//                        Text(text = "Update")
//                    }
//                }
//            }
//        }
//    }
//
//
//                    if (showDialog.value) {
//                        AlertDialog(
//                            onDismissRequest = { showDialog.value = false },
//                            dismissButton = {
//                                Button(
//                                    onClick = {
//                                        showDialog.value = false
//                                        navController.popBackStack()
//                                    }
//                                ) {
//                                    Text(text = "No")
//                                }
//                            },
//                            confirmButton = {
//                                Button(
//                                    onClick = {
//                                        if (customerId != null) {
//                                            viewModel.deleteCustomer(
//                                                customer = CustomerEntity(
//                                                    customerId = customerId.toInt(),
//                                                    name = name,
//                                                    phoneNumber = phoneNumber,
//                                                    email = email,
//                                                    address = address,
//                                                    dob = dob,
//
//                                                )
//                                            )
//                                        }
//                                        showDialog.value = false
//                                        navController.popBackStack()
//                                    }
//                                ) {
//                                    Text(text = "Yes")
//                                }
//                            },
//                            title = {
//                                Text(
//                                    text = "Delete Customer",
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 25.sp
//                                )
//                            },
//                            text = {
//                                Text(
//                                    text = "Are you sure?",
//                                    fontSize = 25.sp
//                                )
//                            }
//                        )
//                    }
//
//                    if (showUpdateDialog.value) {
//                        AlertDialog(
//                            onDismissRequest = { showUpdateDialog.value = false },
//                            dismissButton = {
//                                Button(
//                                    onClick = {
//                                        showUpdateDialog.value = false
//                                        inputName = empty
//                                        inputPhoneNumber = empty
//                                        inputEmail = empty
//                                        inputAddress = empty
//                                        inputDob = empty
//
//                                    }
//                                ) {
//                                    Text(text = "Cancel")
//                                }
//                            },
//                            confirmButton = {
//                                if (inputName.isNotEmpty() || inputPhoneNumber.isNotEmpty() || inputEmail.isNotEmpty() || inputAddress.isNotEmpty() || inputDob.isNotEmpty() ) {
//                                    Button(
//                                        onClick = {
//                                            val updatedCustomer = CustomerEntity(
//                                                customerId = customerId!!.toInt(),
//                                                name = inputName,
//                                                phoneNumber = inputPhoneNumber,
//                                                email = inputEmail,
//                                                address = inputAddress,
//                                                dob = inputDob,
//                                            )
//                                            viewModel.updateCustomer(updatedCustomer)
//                                            navController.popBackStack()
//                                            showUpdateDialog.value = false
//                                            inputName = empty
//                                            inputPhoneNumber = empty
//                                            inputEmail = empty
//                                            inputAddress = empty
//                                            inputDob = empty
//
//                                        }
//                                    ) {
//                                        Text(text = "Update")
//                                    }
//                                }
//                            },
//                            title = {
//                                Text(
//                                    text = "Update Customer",
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 25.sp,
//                                    modifier = Modifier.padding(5.dp)
//                                )
//                            },
//                            text = {
//                                Column {
//                                    OutlinedTextField(
//                                        value = inputName,
//                                        onValueChange = { inputName = it },
//                                        label = { Text(text = "Name") },
//                                        placeholder = { Text(text = "Enter Name") }
//                                    )
//                                    OutlinedTextField(
//                                        value = inputPhoneNumber,
//                                        onValueChange = { inputPhoneNumber = it },
//                                        label = { Text(text = "Phone Number") },
//                                        placeholder = { Text(text = "Enter Phone Number") }
//                                    )
//                                    OutlinedTextField(
//                                        value = inputEmail,
//                                        onValueChange = { inputEmail = it },
//                                        label = { Text(text = "Email") },
//                                        placeholder = { Text(text = "Enter Email") }
//                                    )
//                                    OutlinedTextField(
//                                        value = inputAddress,
//                                        onValueChange = { inputAddress = it },
//                                        label = { Text(text = "Address") },
//                                        placeholder = { Text(text = "Enter Address") }
//                                    )
//                                    OutlinedTextField(
//                                        value = inputDob,
//                                        onValueChange = { inputDob = it },
//                                        label = { Text(text = "Date of Birth") },
//                                        placeholder = { Text(text = "Enter Date of Birth") }
//                                    )
//
//                                }
//                            }
//                        )
//                    }
//                }
//
