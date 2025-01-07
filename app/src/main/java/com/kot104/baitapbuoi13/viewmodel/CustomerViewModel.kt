package com.kot104.baitapbuoi13.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kot104.baitapbuoi13.room.Entity.*
import com.kot104.baitapbuoi13.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CustomerViewModel @Inject constructor (
    private val repository: CustomerRepository) : ViewModel() {

    private val _customers = MutableLiveData<Flow<List<CustomerEntity>>>()
    val customers: Flow<List<CustomerEntity>> = repository.getAllCustomers()


    init {
        fetchCustomers()
    }

    fun fetchCustomers() {
        viewModelScope.launch {
            _customers.value = repository.getAllCustomers()
        }
    }



    fun SearchAllCustomers(keyword: String) : Flow<List<CustomerEntity>>  {
        viewModelScope.launch {
            _customers.value = repository.SearchAllCustomers(keyword)
        }
        return _customers.value!!
    }
     fun getCustomerById(customerId: Int) : Flow<CustomerEntity?> {
        return  repository.getCustomerById(customerId)
    }
    fun addCustomer(customer: CustomerEntity) {
        viewModelScope.launch {
            repository.insertCustomer(customer)
        }
    }

    fun updateCustomer(customer: CustomerEntity) {
        viewModelScope.launch {
            repository.updateCustomer(customer)

        }
    }

    fun deleteCustomer(customer: CustomerEntity) {
        viewModelScope.launch {
            repository.deleteCustomer(customer)

        }
    }

}