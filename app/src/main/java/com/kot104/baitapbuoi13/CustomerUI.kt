package com.kot104.baitapbuoi13

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kot104.baitapbuoi13.room.Entity.CustomerEntity
import com.kot104.baitapbuoi13.viewmodel.CustomerViewModel
import java.time.Instant
import java.util.Calendar
import java.util.Date


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerUi(navController: NavController, viewModel: CustomerViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Dữ liệu khách hàng dùng chung giữa các màn hình
    val customers by viewModel.SearchAllCustomers(searchQuery).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Customer Management",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.background
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 70.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(it).fillMaxSize()) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.SearchAllCustomers(it)
                },
                label = { Text("Search Customer") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                leadingIcon = { // Thêm icon search ở đầu
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search), // Thêm hành động tìm kiếm khi nhấn Enter
                keyboardActions = KeyboardActions(onSearch = { // Xử lý sự kiện nhấn Enter
                    viewModel.SearchAllCustomers(searchQuery)
                })
            )
            if (customers.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "No data")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(bottom = 70.dp)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    items(customers) {
                        Card(
                            onClick = {
                                navController.navigate(
                                    "${ROUTE_NAME_SCREEN.MainTest.name}/${Uri.encode(it.customerId?.toString() ?: "")}/${Uri.encode(it.name ?: "")}/${Uri.encode(it.phoneNumber ?: "")}/${Uri.encode(it.email ?: "")}/${Uri.encode(it.address ?: "")}/${Uri.encode(it.dob ?: "")}"
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                            ) {
                                TestUI( it.name, it.dob!!, it.phoneNumber.toString(), it.email.toString())
                            }
                        }
                    }
                }
            }
        }
        if (showDialog) {
            AddCustomerDialog(
                onDismiss = { showDialog = false },
                onAddCustomer = { customer ->
                    viewModel.addCustomer(customer)
                    showDialog = false
                },
                context = context
            )
        }
    }
}

@Composable
fun AddCustomerDialog(
    onDismiss: () -> Unit,
    onAddCustomer: (CustomerEntity) -> Unit,
    context: Context
) {
    var inputName by remember { mutableStateOf("") }
    var inputPhoneNumber by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf(" ") }
    var inputAddress by remember { mutableStateOf(" ") }
    var inputDob by remember { mutableStateOf(" ") }
    val empty by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                    inputName = empty
                    inputPhoneNumber = empty
                    inputEmail = empty
                    inputAddress = empty
                    inputDob = empty
                }
            ) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            if (inputName.isNotEmpty() && inputPhoneNumber.isNotEmpty()) {
                Button(
                    onClick = {
                        onAddCustomer(
                            CustomerEntity(
                                customerId = 0,
                                name = inputName,
                                phoneNumber = inputPhoneNumber,
                                email = inputEmail,
                                address = inputAddress,
                                dob = inputDob,
                                createdAt = Date.from(Instant.now()),
                                updatedAt = Date.from(Instant.now())
                            )
                        )
                        inputName = empty
                        inputPhoneNumber = empty
                        inputEmail = empty
                        inputAddress = empty
                        inputDob = empty
                    }
                ) {
                    Text(text = "Add")
                }
            }
        },
        title = {
            Text(
                text = "Add Customer",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(5.dp)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .verticalScroll(rememberScrollState())
            )
            {
                OutlinedTextField(
                    value = inputName,
                    onValueChange = { inputName = it },
                    label = { Text(text = "Name *") },
                    placeholder = { Text(text = "Enter customer name") }
                )
                OutlinedTextField(
                    value = inputPhoneNumber,
                    onValueChange = { inputPhoneNumber = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    label = { Text(text = "Phone Number *") },
                    placeholder = { Text(text = "Enter phone number") }
                )
                OutlinedTextField(
                    value = inputEmail,
                    onValueChange = { inputEmail = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    label = { Text(text = "Email") },
                    placeholder = { Text(text = "Enter email address") }
                )
                OutlinedTextField(
                    value = inputAddress,
                    onValueChange = { inputAddress = it },
                    label = { Text(text = "Address") },
                    placeholder = { Text(text = "Enter address") }
                )
                OutlinedTextField(
                    value = inputDob,
                    onValueChange = {},
                    label = { Text(text = "Birthday") },
                    placeholder = { Text(text = "Select Birthday") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Gọi hàm showDatePicker khi nhấn
                            showDatePicker(context = context) { selectedDate ->
                                inputDob = selectedDate
                            }
                        },
                    enabled = false // Ngăn nhập thủ công,
                )


            }
        }
    )
}
fun showDatePicker(
    context: Context,
    initialDate: Calendar = Calendar.getInstance(),
    onDateSelected: (String) -> Unit // Callback khi người dùng chọn ngày
) {
    val year = initialDate.get(Calendar.YEAR)
    val month = initialDate.get(Calendar.MONTH)
    val day = initialDate.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            // Trả về ngày tháng đã chọn thông qua callback
            onDateSelected("$selectedDay/${selectedMonth + 1}/$selectedYear")
        },
        year,
        month,
        day
    )
    datePickerDialog.show()
}