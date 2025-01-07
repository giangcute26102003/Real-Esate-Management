import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kot104.baitapbuoi13.AddressHN.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressInput(
    onDistrictChange: (District?) -> Unit,
    onWardChange: (Ward?) -> Unit,
) {
    val viewModel: AddressViewModel = hiltViewModel()
    val districts by viewModel.districts.collectAsState()
    val wards by viewModel.filteredWards.collectAsState()
    var expandedDistrict by remember { mutableStateOf(false) }
    var inputDistrict by remember { mutableStateOf("") }
    var selectedDistrict by remember { mutableStateOf<District?>(null) }
    var expandedWard by remember { mutableStateOf(false) }
    var inputWard by remember { mutableStateOf("") }
    var selectedWard by remember { mutableStateOf<Ward?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expandedDistrict,
            onExpandedChange = { expandedDistrict = !expandedDistrict }
        ) {
            OutlinedTextField(
                value = inputDistrict,
                onValueChange = {  },
                readOnly = true,
                label = { Text(text = "Quận/Huyện") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDistrict) },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expandedDistrict,
                onDismissRequest = { expandedDistrict = false }
            ) {
                districts.forEach { district ->
                    DropdownMenuItem(text = { Text(text = district.name) }, onClick = {
                        inputDistrict = district.name
                        selectedDistrict = district
                        viewModel.selectDistrict(district)
                        onDistrictChange(district)
                        expandedDistrict = false
                    }
                    )
                }
            }
        }
        ExposedDropdownMenuBox(
            expanded = expandedWard,
            onExpandedChange = { expandedWard = !expandedWard }
        ) {
            OutlinedTextField(
                value = inputWard,
                onValueChange = {  },
                readOnly = true,
                label = { Text(text = "Phường/Xã") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedWard) },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expandedWard,
                onDismissRequest = { expandedWard = false }
            ) {
                wards.forEach { ward ->
                    DropdownMenuItem(text = { Text(text = ward.name) }, onClick = {
                        inputWard = ward.name
                        selectedWard = ward
                        onWardChange(ward)
                        expandedWard = false
                    }
                    )
                }
            }
        }
    }
}