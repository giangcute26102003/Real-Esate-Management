package com.kot104.baitapbuoi13

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kot104.baitapbuoi13.viewmodel.SharedViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import com.kot104.baitapbuoi13.room.Entity.CustomerEntity
import com.kot104.baitapbuoi13.room.Entity.InteractionEntity
import java.time.Instant
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InteractionUI(
    navController: NavController,
    propertyId: Int
) {
    var inputCustomerId by remember { mutableStateOf(0) }
    var inputDate by remember { mutableStateOf("") }
    var inputDetails by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var newCustomerName by remember { mutableStateOf("") }
    var newCustomerPhone by remember { mutableStateOf("") }
    var newCustomerEmail by remember { mutableStateOf("") }
    var isCreatingNewCustomer by remember { mutableStateOf(true) }
    val sharedViewModel: SharedViewModel = hiltViewModel()
    val context = LocalContext.current

    // Get list of customers
    val customers by sharedViewModel.customerViewModel.customers.collectAsState(emptyList())

    // Filter customers based on search query
    val filteredCustomers = customers.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.phoneNumber!!.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(26.dp))
        Text(
            text = "Add Interaction",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it; isCreatingNewCustomer = false },
            label = { Text(text = "Search Customer (Name or Phone)") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear")
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredCustomers.isNotEmpty()) {
            // Dropdown Menu for selecting customer
            var expanded by remember { mutableStateOf(false) }

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = filteredCustomers.find { it.customerId == inputCustomerId }?.name ?: "",
                    onValueChange = {},
                    label = { Text(text = "Select Customer") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown"
                            )
                        }
                    }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    filteredCustomers.forEach { customer ->
                        DropdownMenuItem(
                            onClick = {
                                inputCustomerId = customer.customerId
                                expanded = false
                                searchQuery = ""
                            },
                            text = { Text(customer.name) }
                        )
                    }
                }
            }

            val selectedCustomer = filteredCustomers.find { it.customerId == inputCustomerId }
            if (selectedCustomer != null) {
                isCreatingNewCustomer = false
                Text(
                    text = "Phone: ${selectedCustomer.phoneNumber}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Email: ${selectedCustomer.email}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
         else if (isCreatingNewCustomer) {
            // Input fields for creating a new customer
            OutlinedTextField(
                value = newCustomerName,
                onValueChange = { newCustomerName = it },
                label = { Text(text = "New Customer Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = newCustomerPhone,
                onValueChange = { newCustomerPhone = it },
                label = { Text(text = "New Customer Phone") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = newCustomerEmail,
                onValueChange = { newCustomerEmail = it },
                label = { Text(text = "New Customer Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
        }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputDate,
            onValueChange = { inputDate = it },
            label = { Text(text = "Date (YYYY-MM-DD)") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    // Gọi hàm showDatePicker khi nhấn
                    showDatePicker(context = context) { selectedDate ->
                        inputDate = selectedDate
                    }
                },
            enabled = false ,// Ngăn nhập thủ công,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = inputDetails,
            onValueChange = { inputDetails = it },
            label = { Text(text = "Details") },
            modifier = Modifier.fillMaxWidth()
        )



        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = { navController.popBackStack() }) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                sharedViewModel.interactionViewModel.addInteraction(

                            InteractionEntity(
                                interactionid = 0,
                                propertyid = propertyId,
                                customerid = inputCustomerId,
                                date = inputDate,
                                details = inputDetails,
                                createdat = Date.from(Instant.now()).toString(),
                                updatedat = Date.from(Instant.now()).toString()
                            )
                )
                if(isCreatingNewCustomer){
                    sharedViewModel.customerViewModel.addCustomer(
                      CustomerEntity(
                            customerId = 0,
                            name = newCustomerName,
                            phoneNumber = newCustomerPhone,
                            email = newCustomerEmail?: " ",
                            address = " ",
                            dob = " "
                    )
                    )

                }
            }) {
                Text("Save")
            }
        }
    }
}
