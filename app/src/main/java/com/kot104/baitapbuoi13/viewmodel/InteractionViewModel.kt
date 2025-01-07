package com.kot104.baitapbuoi13.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kot104.baitapbuoi13.room.Entity.*
import com.kot104.baitapbuoi13.repository.InteractionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class InteractionViewModel @Inject constructor(
    private val repository: InteractionRepository) : ViewModel() {




            val interactions = repository.getAllInteractions()


    fun addInteraction(interaction: InteractionEntity) {
        viewModelScope.launch {
            repository.insertInteraction(interaction)

        }
    }

    fun updateInteraction(interaction: InteractionEntity) {
        viewModelScope.launch {
            repository.updateInteraction(interaction)

        }
    }

    fun deleteInteraction(interaction: InteractionEntity) {
        viewModelScope.launch {
            repository.deleteInteraction(interaction)

        }
    }
}