package com.kot104.baitapbuoi13.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kot104.baitapbuoi13.room.Entity.*
import com.kot104.baitapbuoi13.repository.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class PropertyViewModel @Inject constructor (
    private val repository: PropertyRepository) : ViewModel() {



     val properties = repository.getAllProperties()

    fun getPropertyById(propertyId: Int) : Flow<PropertyEntity?> {
        return  repository.getPropertyById(propertyId)
    }
    fun addProperty(property: PropertyEntity) {
        viewModelScope.launch {
            repository.insertProperty(property)
        }
    }

    fun updateProperty(property: PropertyEntity) {
        viewModelScope.launch {
            repository.updateProperty(property)
        }
    }

    fun deleteProperty(property: PropertyEntity) {
        viewModelScope.launch {
            repository.deleteProperty(property)
        }
    }
}