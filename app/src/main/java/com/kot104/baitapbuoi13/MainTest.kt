package com.kot104.baitapbuoi13

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kot104.baitapbuoi13.AddressHN.AddressViewModel
import com.kot104.baitapbuoi13.AddressHN.District
import com.kot104.baitapbuoi13.room.Entity.CustomerEntity
import com.kot104.baitapbuoi13.room.Entity.CustomerRequirementsEntity
import com.kot104.baitapbuoi13.viewmodel.CustomerViewModel
import com.kot104.baitapbuoi13.viewmodel.SharedViewModel
import java.time.Instant
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTest(
    navController: NavController,
    viewModel: CustomerViewModel,
    customerId: String?,
    name: String,
    phoneNumber: String,
    email: String,
    address: String,
    dob: String,
) {
    val viewModel1: AddressViewModel = hiltViewModel()
    val districts by viewModel1.districts.collectAsState()
    var selectedDistrict by remember { mutableStateOf<District?>(null) }
    var inputName by remember { mutableStateOf(name) }
    var inputPhoneNumber by remember { mutableStateOf(phoneNumber) }
    var inputEmail by remember { mutableStateOf(email) }
    var inputAddress by remember { mutableStateOf(address) }
    var inputDob by remember { mutableStateOf(dob) }
    var showUpdateCustomerDialog by remember { mutableStateOf(false) }


    var inputPurpose by remember { mutableStateOf("") }
    var inputBudgetMin by remember { mutableStateOf(0f) }
    var inputBudgetMax by remember { mutableStateOf(100f) }
    var inputPreferredLocation by remember { mutableStateOf("") }
    var expandedDistrict by remember { mutableStateOf(false) }
    var inputPropertyType by remember { mutableStateOf("") }
    var inputBedrooms by remember { mutableStateOf("") }
    var inputSizeMin by remember { mutableStateOf("") }
    var inputBathrooms by remember { mutableStateOf("") }
    var inputOtherPreferences by remember { mutableStateOf("") }

    val rqviewModel: SharedViewModel = hiltViewModel()
    val propertyTypes = listOf("Thổ cư", "Chung cư")
    val purposes = listOf("Đầu tư", "Sử dụng", "Cho Thuê")

    if (customerId != null) {
        if (customerId.isNotEmpty()) {

            val customerRequirements by rqviewModel
                .customerRequirementsViewModel
                .getCustomerRequirementById(customerId.toInt()).collectAsState(initial = CustomerRequirementsEntity(

                    requirementId = customerId.toInt(),
                    customerId = customerId.toInt(),
                    purpose = "",
                    budgetMin = 0.0,
                    budgetMax = 0.0,
                    preferredLocation = "",
                    propertyType = "",
                    sizeMin = null,
                    bedrooms = null,
                    bathrooms = null,
                    otherPreferences = "",
                    createdAt = Date.from(Instant.now()),

                    ))

            inputOtherPreferences = customerRequirements?.otherPreferences.toString()
            inputPurpose = customerRequirements?.purpose.toString()
            inputBudgetMin = customerRequirements?.budgetMin?.toFloat() ?: 0f
            inputBudgetMax = customerRequirements?.budgetMax?.toFloat() ?: 100f
            inputPreferredLocation = customerRequirements?.preferredLocation.toString()
            inputPropertyType = customerRequirements?.propertyType.toString()
            inputSizeMin = customerRequirements?.sizeMin?.toString() ?: ""
            inputBedrooms = customerRequirements?.bedrooms?.toString() ?: ""
            inputBathrooms = customerRequirements?.bathrooms?.toString() ?: ""


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),


                ) {

                Spacer(modifier = Modifier.height(30.dp))
                // Customer Information Section
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                    Text(text = "Thông tin khách hàng", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),

                        onClick = {
                        showUpdateCustomerDialog = true
                    }
                    ) {
                        Image(
                            painterResource(id = R.drawable.write), null,
                        )
                    }

                }
                Box(modifier = Modifier
                    .height(186.dp)
                ){
                    TestUI(inputName, inputEmail, inputPhoneNumber, inputDob)
                }

                Spacer(modifier = Modifier.height(6.dp))
                LazyColumn(
                    modifier = Modifier
                        .padding()
                        .padding(5.dp)
                        .fillMaxSize()
                        .weight(1f),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    items(1) {
                        Card(
                            onClick = {

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp)
                            ) {
                                // Mục đích (Dropdown)
                                DropdownMenuComponent(
                                    label = "Mục đích",
                                    options = purposes,
                                    selectedOption = inputPurpose,
                                    onOptionSelected = { inputPurpose = it }
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Ngân sách tối thiểu và tối đa (Slider)
                                Text(text = "Ngân sách (${inputBudgetMin.toInt()} - ${inputBudgetMax.toInt()}) tỷ đồng")
                                RangeSlider(
                                    value = inputBudgetMin..inputBudgetMax,
                                    steps = 100,
                                    onValueChange = { range ->
                                        inputBudgetMin = range.start
                                        inputBudgetMax = range.endInclusive
                                    },
                                    valueRange = 0f..100f

                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Vị trí ưu tiên (Dropdown)

                                ExposedDropdownMenuBox(
                                    expanded = expandedDistrict,
                                    onExpandedChange = { expandedDistrict = !expandedDistrict }
                                ) {
                                    OutlinedTextField(
                                        value = inputPreferredLocation,
                                        onValueChange = { },
                                        readOnly = true,
                                        label = { Text(text = "Quận/Huyện") },
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expandedDistrict
                                            )
                                        },

                                        modifier = Modifier.fillMaxWidth().menuAnchor()
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expandedDistrict,
                                        onDismissRequest = { expandedDistrict = false }
                                    ) {
                                        districts.forEach { district ->
                                            DropdownMenuItem(text = { Text(text = district.name) }, onClick = {
                                                inputPreferredLocation = district.name
                                                selectedDistrict = district
                                                viewModel1.selectDistrict(district)
                                                expandedDistrict = false
                                            }
                                            )
                                        }
                                    }
                                }


                                Spacer(modifier = Modifier.height(8.dp))

                                // Loại bất động sản (Dropdown)
                                DropdownMenuComponent(
                                    label = "Loại bất động sản",
                                    options = propertyTypes,
                                    selectedOption = inputPropertyType,
                                    onOptionSelected = { inputPropertyType = it }
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Các trường khác (Số phòng ngủ, Số phòng tắm)
                                OutlinedTextField(
                                    value =  inputSizeMin,
                                    onValueChange = { inputSizeMin = it },
                                    label = { Text(text = "diện tích tối thiểu") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = inputBedrooms,
                                    onValueChange = { inputBedrooms = it },
                                    label = { Text(text = "Số phòng ngủ") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = inputBathrooms,
                                    onValueChange = { inputBathrooms = it },
                                    label = { Text(text = "Số phòng tắm") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = inputOtherPreferences,
                                    onValueChange = { inputOtherPreferences = it },
                                    label = { Text(text = "Ưu tiên khác") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }




                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,verticalAlignment = Alignment.CenterVertically){

                    Button(onClick = {

                        viewModel.updateCustomer(
                            CustomerEntity(
                                customerId.toInt(),
                                inputName,
                                inputPhoneNumber,
                                inputEmail,
                                inputAddress,
                                inputDob,
                                createdAt = Date.from(Instant.now()),
                                updatedAt = Date.from(Instant.now())


                            )
                        )
                        rqviewModel.customerRequirementsViewModel.updateRequirement(
                            CustomerRequirementsEntity(

                                requirementId = customerId.toInt(),
                                customerId.toInt(), // Tham chiếu sau khi lưu
                                purpose = inputPurpose,
                                budgetMin = inputBudgetMin.toDouble(),
                                budgetMax = inputBudgetMax.toDouble(),
                                preferredLocation = inputPreferredLocation,
                                propertyType = inputPropertyType,
                                sizeMin = inputSizeMin.toDoubleOrNull(),
                                bedrooms = inputBedrooms.toIntOrNull(),
                                bathrooms = inputBathrooms.toIntOrNull(),
                                otherPreferences = inputOtherPreferences,
                                createdAt = Date.from(Instant.now()),
                                updatedAt = Date.from(Instant.now())

                            )
                        )
                    }
                    ) {
                        Text(text = "Lưu")
                    }
                    Button(
                        onClick = {
                            navController.navigate("${ROUTE_NAME_SCREEN.PropertyUI.name}/$customerId") }
                    ) {
                        Text(text = "Xem bất động sản")
                    }
                }

            }


        }
        else {
            Text(text = "Khách hàng không tồn tại")
        }
    }
    if (showUpdateCustomerDialog) {
        UpdateCustomerDialog(
            initialState = UpdateCustomerState(
                name = inputName,
                phoneNumber = inputPhoneNumber,
                email = inputEmail,
                address = inputAddress,
                dob = inputDob
            ),
            onDismiss = { showUpdateCustomerDialog = false },
            onUpdate = { newState ->
                viewModel.updateCustomer(
                    CustomerEntity(
                        customerId = customerId!!.toInt(),
                        name = newState.name,
                        phoneNumber = newState.phoneNumber,
                        email = newState.email,
                        address = newState.address,
                        dob = newState.dob,
                        createdAt = Date.from(Instant.now()),
                        updatedAt = Date.from(Instant.now())
                    )
                )
                inputName = newState.name
                inputPhoneNumber = newState.phoneNumber
                inputEmail = newState.email
                inputAddress = newState.address
                inputDob = newState.dob
                showUpdateCustomerDialog = false
            }
        )
    }
}


@Composable
fun DropdownMenuComponent(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            label = { Text(text = label) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .clickable { expanded = true }
        )
    }
}
data class UpdateCustomerState(
    val name: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val address: String = "",
    val dob: String = ""
)

@Composable
fun UpdateCustomerDialog(
    initialState: UpdateCustomerState,
    onDismiss: () -> Unit,
    onUpdate: (UpdateCustomerState) -> Unit
) {
    var state by remember { mutableStateOf(initialState) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Update Customer",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(5.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = { state = state.copy(name = it) },
                        label = { Text(text = "Name") },
                        placeholder = { Text(text = "Enter name") }
                    )
                    OutlinedTextField(
                        value = state.phoneNumber,
                        onValueChange = { state = state.copy(phoneNumber = it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text(text = "Phone") },
                        placeholder = { Text(text = "Enter phone number") }
                    )
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { state = state.copy(email = it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        label = { Text(text = "Email") },
                        placeholder = { Text(text = "Enter email") }
                    )
                    OutlinedTextField(
                        value = state.address,
                        onValueChange = { state = state.copy(address = it) },
                        label = { Text(text = "Address") },
                        placeholder = { Text(text = "Enter address") }
                    )
                    OutlinedTextField(
                        value = state.dob,
                        onValueChange = { state = state.copy(dob = it) },
                        label = { Text(text = "Date of birth") },
                        placeholder = { Text(text = "Enter date of birth") }
                    )

                }
                Row(modifier = Modifier.padding(top = 16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.End){
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            onUpdate(state)
                        },
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                    ) {
                        Text(text = "Update")
                    }
                }
            }
        }
    }
}