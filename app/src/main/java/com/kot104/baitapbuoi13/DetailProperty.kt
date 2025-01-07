package com.kot104.baitapbuoi13

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kot104.baitapbuoi13.room.Entity.CustomerEntity
import com.kot104.baitapbuoi13.room.Entity.CustomerRequirementsEntity
import com.kot104.baitapbuoi13.room.Entity.InteractionEntity
import com.kot104.baitapbuoi13.room.Entity.PropertyEntity
import com.kot104.baitapbuoi13.viewmodel.PropertyViewModel
import com.kot104.baitapbuoi13.viewmodel.SharedViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailProperty(
    navController: NavController,
    viewModel: PropertyViewModel,
    propertyId: String

) {
    val showDialog = remember { mutableStateOf(false) }
    val showUpdateDialog = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var showBottomSheet by remember { mutableStateOf(false) }

    val property by viewModel.getPropertyById(propertyId.toInt()).collectAsState(initial = null)


    val addressProperty = property?.AddressProperty ?: ""
    val propertyType = property?.propertyType ?: ""
    val size = property?.size?.toString() ?: ""
    val floor = property?.floor?.toString() ?: ""
    val imageUrl = property?.imageUrl ?: ""
    val bedrooms = property?.bedrooms?.toString() ?: ""
    val bathrooms = property?.bathrooms?.toString() ?: ""
    val description = property?.description ?: ""
    val phoneOwner = property?.phoneOwner ?: ""
    val legalDocuments = property?.legalDocuments ?: ""
    val price = property?.price?.toString() ?: ""
    val availability = property?.availability ?: ""

    val sharedViewModel: SharedViewModel = hiltViewModel()

    // Get list of customers
    val customers by sharedViewModel.customerRequirementsViewModel.requirement.collectAsState(emptyList())


    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetElevation = 50.dp,
        sheetContent = {
            if (showBottomSheet) {
                CustomerRequirementSheet(
                    customers = customers,
                    propertyType = propertyType,
                    bedrooms = bedrooms.toIntOrNull() ?: 0,
                    bathrooms = bathrooms.toIntOrNull() ?: 0,
                    size = size.toDoubleOrNull() ?: 0.0,
                    price = price.toDoubleOrNull() ?: 0.0,

                    onClose = {
                        scope.launch { modalSheetState.hide() }
                        showBottomSheet = false
                    }
                )
            } else {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = "No matching customer requirements", fontSize = 20.sp)
                }
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                PropertyDetailAction(
                    onAddInteractionClick = { navController.navigate("${ROUTE_NAME_SCREEN.InteractionUI.name}/${propertyId.toInt()}") },
                    onRequestAppointmentClick = {
                        if (customers.isNotEmpty()) {
                            scope.launch {
                                modalSheetState.show()
                                showBottomSheet = true
                            }
                        } else {
                            scope.launch {
                                modalSheetState.show()
                                showBottomSheet = false
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                PropertyDetailHeader(
                    imageUrl = imageUrl,
                    onBackClick = { navController.popBackStack() },
                    onEditClick = { showUpdateDialog.value = true },
                    onDeleteClick = { showDialog.value = true }
                )
                PropertyDetailInfo(
                    propertyType = propertyType,
                    AddressProperty = addressProperty,
                    price = price,
                    bedrooms = bedrooms,
                    bathrooms = bathrooms,
                    size = size,
                    floor = floor,
                    phoneOwner = phoneOwner,
                    legalDocuments = legalDocuments,
                    description = description,
                    availability = availability
                )
            }
        }

    }


    if (showDialog.value) {
        DeleteConfirmationDialog(
            onDismiss = { showDialog.value = false },
            onConfirm = {
                viewModel.deleteProperty(
                    property = PropertyEntity(
                        propertyId = propertyId.toInt(),
                        AddressProperty = addressProperty,
                        propertyType = propertyType,
                        floor = floor?.toIntOrNull(),
                        size = size.toDoubleOrNull() ?: 0.0,
                        imageUrl = imageUrl,
                        bedrooms = bedrooms.toIntOrNull() ?: 0,
                        bathrooms = bathrooms.toIntOrNull() ?: 0,
                        phoneOwner = phoneOwner,
                        description = description,
                        price = price.toDoubleOrNull() ?: 0.0,
                        legalDocuments = legalDocuments,
                        availability = availability
                    )
                )
                showDialog.value = false
                navController.popBackStack()
            }
        )
    }
    if (showUpdateDialog.value) {
        UpdatePropertyDialog(
            initialState =  UpdatePropertyState(
                address = addressProperty,
                propertyType = propertyType,
                size = size,
                floor = floor ?: "",
                imageUrl = imageUrl,
                bedrooms = bedrooms,
                bathrooms = bathrooms,
                legalDocuments = legalDocuments,
                phoneOwner = phoneOwner,
                description = description,
                price = price,
                availability = availability
            ),
            onDismiss = { showUpdateDialog.value = false },
            onUpdate = { newState ->
                val newProperty = PropertyEntity(
                    propertyId = propertyId.toInt(),
                    AddressProperty = newState.address,
                    propertyType = newState.propertyType,
                    size = newState.size.toDoubleOrNull() ?: 0.0,
                    floor = newState.floor.toIntOrNull(),
                    imageUrl = newState.imageUrl,
                    bedrooms = newState.bedrooms.toIntOrNull() ?: 0,
                    bathrooms = newState.bathrooms.toIntOrNull() ?: 0,
                    description = newState.description,
                    phoneOwner = newState.phoneOwner,
                    legalDocuments = newState.legalDocuments,
                    price = newState.price.toDoubleOrNull() ?: 0.0,
                    availability = newState.availability
                )
                viewModel.updateProperty(newProperty)
                navController.popBackStack()
                showUpdateDialog.value = false
            },

            )
    }
}


@Composable
fun PropertyDetailHeader(
    imageUrl: String,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Box(modifier = Modifier.height(390.dp)) {
        Image(
            painter = painterResource(id = R.drawable.image1),
            contentDescription = "Detail Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back Button",
                modifier = Modifier
                    .clickable {
                        onBackClick()
                    }
            )
            Row {
                IconButton(onClick = onEditClick) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Property")
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Property")
                }
            }
        }
    }
}


@Composable
fun PropertyDetailInfo(
    propertyType: String,
    AddressProperty: String,
    price: String,
    phoneOwner: String,
    legalDocuments: String,
    description: String,
    availability: String,
    bedrooms: String,
    bathrooms: String,
    size: String,
    floor: String?
){

    Text(
        text = AddressProperty,
        color = Color.Black,
        style = TextStyle(fontSize = 26.sp),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
    Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp),
    verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width( 6.dp))
        ContactItem(
            iconId = R.drawable.moneybag, // Replace with your icon
            text =price +" tỷ" ,
            isPhone = true

        )
        Spacer(modifier = Modifier.width( 168.dp))
        val textColor = when (availability) {
            "Available" -> Color.Green
            "Unavailable" -> Color.Red
            "Deposited" -> Color.Yellow
            else -> Color.Gray // Default color
        }

        Text(
            text = availability,
            color = textColor,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.Text(
            text = "Thông số:",
            color = Color.Black,
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f)
        )
        ContactItem(
            iconId = R.drawable.phonecall, // Replace with your icon
            text = phoneOwner,
            isPhone = true

        )



    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        FacilityItem(iconId = R.drawable.bed, text = "Bedroom",bedrooms, modifier = Modifier.weight(0.25f))
        FacilityItem(iconId = R.drawable.bath, text = "Bathroom",bathrooms, modifier = Modifier.weight(0.25f))
        FacilityItem(iconId = R.drawable.size, text = "Size",size, modifier = Modifier.weight(0.25f))
        FacilityItem(iconId = R.drawable.cat_2, text = "floor", floor.toString(), modifier = Modifier.weight(0.25f))
    }
    Row(
        Modifier.padding(start = 10.dp)
    ) {   ContactItem(

        iconId = R.drawable.book, // Replace with your icon
        text = legalDocuments,
        isPhone = true

    )
        Spacer(modifier = Modifier.width( 128.dp))
        Text(
            text = propertyType,
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.grey),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 16.dp)
                .height(25.dp),
            color = colorResource(id = R.color.black),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),

            )}

    androidx.compose.material.Text(
        text = description,
        color = Color.Black,
        modifier = Modifier.padding(18.dp)
    )

}




@Composable
fun PropertyDetailAction(
    onAddInteractionClick: () -> Unit,
    onRequestAppointmentClick: () -> Unit,
    modifier: Modifier = Modifier // Pass the modifier to the function
){
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(60.dp)
                .background(
                    color = colorResource(id = R.color.teal_200),
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable(onClick = onAddInteractionClick),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "ADD Interaction"
            )
        }
        androidx.compose.material.Button(
            onClick = onRequestAppointmentClick,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.teal_700)),
        ) {
            androidx.compose.material.Text(
                text = "Suggest Customer",
                color = Color.White,
                fontSize = 18.sp
            )
        }

    }

}
@Composable
fun CustomerRequirementSheet(
    customers: List<CustomerRequirementsEntity>,
    propertyType: String,
    bedrooms: Int,
    bathrooms: Int,
    size: Double,
    price: Double,
    onClose: () -> Unit
) {

    val filteredCustomers = customers.filter { customer ->
        (customer.propertyType?.contains(propertyType, ignoreCase = true) ?: true) &&
//                (customer.bedrooms ?: 0) >= bedrooms &&
                (customer.budgetMin ?: 0.0) <= price &&
//                (customer.bathrooms ?: 0) >= bathrooms &&
                (customer.sizeMin ?: 0.0) <= size
    }
    if (filteredCustomers.isNotEmpty()) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Customers", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                TextButton(onClick = onClose) {
                    Text(text = "Close", color = Color.Gray)
                }
            }
            LazyColumn(modifier = Modifier.weight(1f)){ // This makes the list take up most of the space
                items(filteredCustomers) { customer ->
                    CustomerRequirementItem(customerRequirements = customer)
                }
            }
        }
    } else {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No customers match property requirements.")
        }
    }
}
@Composable
fun CustomerRequirementItem(customerRequirements: CustomerRequirementsEntity){
    val sharedViewModel: SharedViewModel = hiltViewModel()
    val customer by sharedViewModel.customerViewModel.getCustomerById(customerRequirements.customerId).collectAsState(initial = null)
    customer?.let {
        CustomerItem(customer = it)
    }

}
@Composable
fun CustomerItem(customer: CustomerEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "Name: ${customer.name}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "Email: ${customer.email}", fontSize = 14.sp)
            Text(text = "Phone: ${customer.phoneNumber}", fontSize = 14.sp)
        }
    }
}

@Composable
fun FacilityItem(iconId: Int, text: String, quantity: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(end = 4.dp)
            .background(
                color = colorResource(id = R.color.white),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 3.dp, vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier
                    .height(22.dp)

            )
            androidx.compose.material.Text(
                text = ": " + quantity,
                color = Color.Black,
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        androidx.compose.material.Text(
            text = text,
            color = Color.Black,
            style = TextStyle(fontSize = 12.sp),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}



@Composable
fun DeleteConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(text = "No")
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(text = "Yes")
            }
        },
        title = {
            Text(
                text = "Delete Property",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        },
        text = {
            Text(
                text = "Are you sure?",
                fontSize = 25.sp
            )
        }
    )
}

data class UpdatePropertyState(
    val address: String = "",
    val propertyType: String = "",
    val size: String = "",
    val floor: String = "",
    val imageUrl: String = "",
    val bedrooms: String = "",
    val bathrooms: String = "",
    val legalDocuments: String = "",
    val phoneOwner: String = "",
    val description: String = "",
    val price: String = "",
    val availability: String = ""
)

@Composable
fun UpdatePropertyDialog(
    initialState: UpdatePropertyState,
    onDismiss: () -> Unit,
    onUpdate: (UpdatePropertyState) -> Unit,
) {
    var state by remember { mutableStateOf(initialState) }
    val StatusTypes = listOf("Availiable", "Unavailable", "Deposited")
    val PropertyTypes = listOf("Chung cư", "Thổ cư")
    val LegalTypes = listOf("Sổ đỏ chính chủ", "Vi Bằng" ,"Không giấy tờ")

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Update Property",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(5.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = state.address,
                        onValueChange = { state = state.copy(address = it) },
                        label = { Text(text = "Address") },
                        placeholder = { Text(text = "Enter Address") }
                    )
                    DropdownMenuComponent(
                        label = "Property Type",
                        options = PropertyTypes,
                        selectedOption = state.propertyType,
                        onOptionSelected = {  state = state.copy(propertyType = it)}
                    )
                    Row {
                        OutlinedTextField(
                            value = state.size,
                            onValueChange = { state = state.copy(size = it) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = { Text(text = "Size (sq ft)") },
                            placeholder = { Text(text = "Enter property size") },
                            modifier = Modifier.weight(1f).padding(end = 8.dp)
                        )
                        OutlinedTextField(
                            value = state.floor,
                            onValueChange = { state = state.copy(floor = it) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = { Text(text = "Floor") },
                            placeholder = { Text(text = "Enter floor number (if any)") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    OutlinedTextField(
                        value = state.imageUrl,
                        onValueChange = { state = state.copy(imageUrl = it) },
                        label = { Text(text = "Image URL") },
                        placeholder = { Text(text = "Enter image URL") }
                    )

                    Row {
                        OutlinedTextField(
                            value = state.bedrooms,
                            onValueChange = { state = state.copy(bedrooms = it) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = { Text(text = "Bedrooms") },
                            placeholder = { Text(text = "Enter number of bedrooms") },
                            modifier = Modifier.weight(1f).padding(end = 8.dp)
                        )
                        OutlinedTextField(
                            value = state.bathrooms,
                            onValueChange = { state = state.copy(bathrooms = it) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = { Text(text = "Bathrooms") },
                            placeholder = { Text(text = "Enter number of bathrooms") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    OutlinedTextField(
                        value = state.description,
                        onValueChange = { state = state.copy(description = it) },
                        label = { Text(text = "Features") },
                        placeholder = { Text(text = "Enter additional Description ") }
                    )
                    OutlinedTextField(
                        value = state.price,
                        onValueChange = { state = state.copy(price = it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text(text = "Price") },
                        placeholder = { Text(text = "Enter price") }
                    )

                    DropdownMenuComponent(
                        label = "Availability",
                        options = StatusTypes,
                        selectedOption = state.availability,
                        onOptionSelected = {  state = state.copy(availability = it)}
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
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(text = "Update")
                    }
                }
            }
        }
    }
}