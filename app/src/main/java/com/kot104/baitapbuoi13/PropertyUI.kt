package com.kot104.baitapbuoi13
import AddressInput
import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kot104.baitapbuoi13.AddressHN.District
import com.kot104.baitapbuoi13.AddressHN.Ward
import com.kot104.baitapbuoi13.R
import com.kot104.baitapbuoi13.room.Entity.CustomerRequirementsEntity
import com.kot104.baitapbuoi13.room.Entity.PropertyEntity
import com.kot104.baitapbuoi13.viewmodel.PropertyViewModel
import com.kot104.baitapbuoi13.viewmodel.SharedViewModel
import java.time.Instant
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyUi(navController: NavController, viewModel: PropertyViewModel,requirementId : Int) {
    var showDialog by remember { mutableStateOf(false) }
    val properties by viewModel.properties.collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }
    val sharedViewModel: SharedViewModel = hiltViewModel()
    val customerRequirements by sharedViewModel.customerRequirementsViewModel.getCustomerRequirementById(requirementId).collectAsState(
        initial =
        CustomerRequirementsEntity(
            requirementId =requirementId,
            customerId = requirementId,
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
            )
    )
    val filteredProperties = if (searchQuery.isEmpty()) {
       filterProperties(properties, customerRequirements)
    } else {
        properties.filter { property ->
            property.AddressProperty.contains(searchQuery, ignoreCase = true) ||
                    property.propertyType.contains(searchQuery, ignoreCase = true) ||
                    property.description!!.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Property Management",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape,
                modifier = Modifier
                    .padding(bottom = 128.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                label = { Text("Search") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon"
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    // You can add any specific action here
                })
            )

            AddPropertyScreen(
                properties = filteredProperties,
                navController = navController,
                paddingValues = PaddingValues(0.dp) // Pass zero padding since it's already handled
            )
        }
    }

    if (showDialog) {
        AddPropertyDialog(
            onDismiss = { showDialog = false },
            onAddProperty = { property ->
                viewModel.addProperty(property)
                showDialog = false
            }
        )
    }
}

@Composable
fun AddPropertyScreen(properties: List<PropertyEntity>, navController: NavController, paddingValues: PaddingValues) {
    if (properties.isEmpty()) {
        EmptyState(paddingValues = paddingValues)
    } else {
        PropertyList(properties = properties, navController = navController, paddingValues = paddingValues)
    }
}

@Composable
fun EmptyState(paddingValues: PaddingValues) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(paddingValues)
            .padding(bottom = 70.dp)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.cat_2),
            contentDescription = "No Data Icon",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No properties to show",
            style = TextStyle(fontSize = 16.sp, color = Color.Gray)
        )
    }
}

@Composable
fun PropertyList(properties: List<PropertyEntity>, navController: NavController, paddingValues: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(bottom = 70.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(properties) { property ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                PropertyItem(property = property, navController = navController)
            }

        }
    }
}
@Composable
fun PropertyItem(property: PropertyEntity, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable {
                navController.navigate("${ROUTE_NAME_SCREEN.DetailProperty.name}/${property.propertyId}/")
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier.height(250.dp)) {
            Image(
                painter = painterResource(id = R.drawable.image1),
                contentDescription = "Property Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(id = R.drawable.favorite),
                contentDescription = "Favorite Icon",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .background(
                        color = colorResource(id = R.color.half_black),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location),
                    contentDescription = "Location Icon",
                    modifier = Modifier
                        .padding(end = 1.dp, bottom = 15.dp)
                        .size(16.dp)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = property.AddressProperty,
                        color = Color.White,
                        style = TextStyle(fontSize = 14.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${property.price} Tỷ",
                        color = Color.White,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PropertyItemInfo(icon = R.drawable.size, value = property.size.toString())
                    PropertyItemInfo(icon = R.drawable.bed, value = property.bedrooms.toString())
                    PropertyItemInfo(icon = R.drawable.bath, value = property.bathrooms.toString())
                }
            }
        }
    }
}


@Composable
fun PropertyItemInfo(icon: Int, value: String) {
    Image(
        painter = painterResource(id = icon),
        contentDescription = null,
        modifier = Modifier.size(18.dp)
    )
    Text(
        text = value,
        color = Color.White,
        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp),
        modifier = Modifier.padding(start = 4.dp, end = 8.dp)
    )
}

@Composable
fun AddPropertyDialog(onDismiss: () -> Unit, onAddProperty: (PropertyEntity) -> Unit) {
    var inputAddress by remember { mutableStateOf("") }
    var inputPropertyType by remember { mutableStateOf("") }
    var inputSize by remember { mutableStateOf("") }
    var inputFloor by remember { mutableStateOf("") }
    var inputImageUrl by remember { mutableStateOf("") }
    var inputBedrooms by remember { mutableStateOf("") }
    var inputBathrooms by remember { mutableStateOf("") }
    var inputDescription by remember { mutableStateOf("") }
    var inputPhoneOwner by remember { mutableStateOf("") }
    var inputPrice by remember { mutableStateOf("") }
    var inputLegalDocuments by remember { mutableStateOf("") }
    var inputAvailability by remember { mutableStateOf("") }

    var selectedDistrict by remember { mutableStateOf<District?>(null) }
    var selectedWard by remember { mutableStateOf<Ward?>(null) }
    val emty by remember { mutableStateOf("") }
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
                    text = "Add Property",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(5.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                        AddressInput(
                            onDistrictChange = {selectedDistrict = it},
                            onWardChange = {selectedWard = it}
                        )

                    OutlinedTextField(
                        value = inputAddress   ,
                        onValueChange = { inputAddress = it  },
                        label = { Text(text = "Address ") },
                        placeholder = { Text(text = "Enter Address ") },


                    )
                    DropdownMenuComponent(
                        label = "Property Type",
                        options = PropertyTypes,
                        selectedOption = inputPropertyType,
                        onOptionSelected = { inputPropertyType = it }
                    )

                    Row {
                        OutlinedTextField(
                            value = inputSize,
                            onValueChange = { inputSize = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = { Text(text = "Size (sq ft)") },
                            placeholder = { Text(text = "Enter property size") },
                            modifier = Modifier.weight(1f).padding(end = 8.dp)
                        )
                        OutlinedTextField(
                            value = inputFloor,
                            onValueChange = { inputFloor = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = { Text(text = "Floor") },
                            placeholder = { Text(text = "Enter floor number (if any)") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    OutlinedTextField(
                        value = inputImageUrl,
                        onValueChange = { inputImageUrl = "null" },
                        label = { Text(text = "Image URL") },
                        placeholder = { Text(text = "Enter image URL") },
                        enabled = false
                    )
                    Row {
                        OutlinedTextField(
                            value = inputBedrooms,
                            onValueChange = { inputBedrooms = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = { Text(text = "Bedrooms") },
                            placeholder = { Text(text = "Enter number of bedrooms") },
                            modifier = Modifier.weight(1f).padding(end = 8.dp)
                        )
                        OutlinedTextField(
                            value = inputBathrooms,
                            onValueChange = { inputBathrooms = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = { Text(text = "Bathrooms") },
                            placeholder = { Text(text = "Enter number of bathrooms") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    OutlinedTextField(
                        value = inputPhoneOwner,
                        onValueChange = {  inputPhoneOwner = it },
                        label = { Text(text = "PhoneOwner") },
                        placeholder = { Text(text = "Enter additional PhoneOwner ") },
                    )

                    DropdownMenuComponent(
                        label = "Enter additional LegalDocuments ",
                        options = LegalTypes,
                        selectedOption = inputLegalDocuments,
                        onOptionSelected = { inputLegalDocuments = it }
                    )
                    OutlinedTextField(
                        value = inputDescription,
                        onValueChange = { inputDescription = it },
                        label = { Text(text = "Description") },
                        placeholder = { Text(text = "Enter additional Description ") },
                    )
                    OutlinedTextField(
                        value = inputPrice,
                        onValueChange = { inputPrice = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text(text = "Price") },
                        placeholder = { Text(text = "Enter price") }
                    )
                    DropdownMenuComponent(
                        label = "Availability",
                        options = StatusTypes,
                        selectedOption = inputAvailability,
                        onOptionSelected = { inputAvailability = it }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
                Row(modifier = Modifier.padding(top = 16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.End){
                    TextButton(onClick = {
                        onDismiss()
                        inputAddress = emty
                        inputPropertyType = emty
                        inputSize = emty
                        inputFloor = emty
                        inputImageUrl = emty
                        inputBedrooms = emty
                        inputBathrooms = emty
                        inputDescription = emty
                        inputPhoneOwner = emty
                        inputPrice = emty
                        inputAvailability = emty
                    }) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    if (inputPropertyType.isNotEmpty() && inputSize.isNotEmpty() && inputPrice.isNotEmpty()) {
                        Button(
                            onClick = {
                                onAddProperty(
                                    PropertyEntity(
                                        propertyId = 0,
                                        inputAddress  + "," + selectedWard?.name+ "," + selectedDistrict?.name,
                                        inputPropertyType,
                                        inputSize.toDouble(),
                                        inputFloor.toInt(),
                                        inputImageUrl,
                                        inputBedrooms.toInt(),
                                        inputBathrooms.toInt(),
                                        inputDescription,
                                        inputPrice.toDouble(),
                                        inputLegalDocuments,
                                        inputAvailability,
                                        inputPhoneOwner,

                                        createdAt = Date.from(Instant.now()),
                                        updatedAt = Date.from(Instant.now())
                                    )
                                )
                                inputAddress = emty
                                inputPropertyType = emty
                                inputSize = emty
                                inputFloor = emty
                                inputImageUrl = emty
                                inputBedrooms = emty
                                inputBathrooms = emty
                                inputDescription = emty
                                inputPhoneOwner = emty
                                inputPrice = emty
                                inputAvailability = emty
                            },
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(text = "Save")
                        }
                    }
                }
            }
        }
    }
}