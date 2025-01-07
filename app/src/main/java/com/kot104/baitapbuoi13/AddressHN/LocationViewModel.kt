package com.kot104.baitapbuoi13.AddressHN

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor() : ViewModel() {

    private val _districts = MutableStateFlow<List<District>>(emptyList())
    val districts: StateFlow<List<District>> = _districts

    private val _wards = MutableStateFlow<List<Ward>>(emptyList())
    val wards: StateFlow<List<Ward>> = _wards

    private val _selectedDistrict = MutableStateFlow<District?>(null)
    val selectedDistrict: StateFlow<District?> = _selectedDistrict

    private val _filteredWards = MutableStateFlow<List<Ward>>(emptyList())
    val filteredWards: StateFlow<List<Ward>> = _filteredWards


    init {
        fetchDistricts()
        fetchWards()
    }

    private fun fetchDistricts() {
        viewModelScope.launch {
            try {
                _districts.value = RetrofitClient.api.getDistricts().filter { it.province_code == 1 }
            } catch (e: Exception) {
                Log.e("AddressViewModel", "fetchDistricts error", e)
            }
        }
    }
    private fun fetchWards() {
        viewModelScope.launch {
            try {
                _wards.value = RetrofitClient.api.getWards()

            } catch (e: Exception) {
                Log.e("AddressViewModel", "fetchWards error", e)
            }
        }
    }

    fun selectDistrict(district: District) {
        _selectedDistrict.value = district
        filterWards(district.code )
    }
    private fun filterWards(districtCode: Int){
        _filteredWards.value = _wards.value.filter { it.district_code == districtCode}
    }
}