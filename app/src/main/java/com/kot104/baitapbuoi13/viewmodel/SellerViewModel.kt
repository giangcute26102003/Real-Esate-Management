package com.kot104.baitapbuoi13.viewmodel;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kot104.baitapbuoi13.room.Entity.*
import com.kot104.baitapbuoi13.repository.SellerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SellerViewModel @Inject constructor(
    private val repository: SellerRepository) : ViewModel() {

    private val _sellers = MutableLiveData<Flow<SellerEntity>>()
    val seller: Flow<SellerEntity> = repository.getAllSellers()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        fetchSellers()
    }

    fun fetchSellers() {
        _loading.value = true
        viewModelScope.launch {
            _sellers.value = repository.getAllSellers()
            _loading.value = false
        }
    }

    fun addSeller(seller: SellerEntity) {
        viewModelScope.launch {
            repository.insertSeller(seller)
            fetchSellers()
        }
    }

    fun updateSeller(seller: SellerEntity) {
        viewModelScope.launch {
            repository.updateSeller(seller)
            fetchSellers()
        }
    }

    fun deleteSeller(seller: SellerEntity) {
        viewModelScope.launch {
            repository.deleteSeller(seller)
            fetchSellers()
        }
    }
}
