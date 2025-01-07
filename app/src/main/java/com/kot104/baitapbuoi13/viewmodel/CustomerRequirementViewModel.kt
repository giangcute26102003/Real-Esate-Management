package com.kot104.baitapbuoi13.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kot104.baitapbuoi13.room.Entity.*
import com.kot104.baitapbuoi13.repository.CustomerRequirementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CustomerRequirementViewModel @Inject constructor
    ( val repository: CustomerRequirementRepository) : ViewModel() {


    val requirement: Flow<List<CustomerRequirementsEntity>> = repository.getAllCustomerRequirements()
    fun getCustomerRequirementById(customerId : Int) :Flow<CustomerRequirementsEntity?>  {
         return  repository.getCustomerRequirementById(customerId)
        }

    fun getCustomerRequirementById1(customerId: Int): Flow<CustomerRequirementsEntity> {
        return repository.getCustomerRequirementById(customerId)
    }
    fun addCustomerRequirement(requirement: CustomerRequirementsEntity) {
        viewModelScope.launch {
            repository.insertCustomerRequirement(requirement)

        }
    }

    fun updateRequirement(requirement: CustomerRequirementsEntity) {
        viewModelScope.launch {
            repository.updateCustomerRequirement(requirement)

        }
    }

    fun deleteRequirement(requirement: CustomerRequirementsEntity) {
        viewModelScope.launch {
            repository.deleteCustomerRequirement(requirement)

        }
    }
}
