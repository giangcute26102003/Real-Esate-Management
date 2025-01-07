package com.kot104.baitapbuoi13.AddressHN

import AddressInput
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import java.lang.reflect.Modifier

@Composable
@Preview(showBackground = true)
fun MyScreen(){
    var selectedDistrict by remember { mutableStateOf<District?>(null) }
    var selectedWard by remember { mutableStateOf<Ward?>(null) }
    AddressInput(
        onDistrictChange = {selectedDistrict = it},
        onWardChange = {selectedWard = it}
    )

    Text(text = "District: ${selectedDistrict?.name ?: ""}")
    Text(text = "Ward: ${selectedWard?.name ?: ""}")
}