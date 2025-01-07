package com.kot104.baitapbuoi13.viewmodel

import androidx.lifecycle.ViewModel
import com.kot104.baitapbuoi13.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val studentRepository: Repository,
    private val customerRepository: CustomerRepository,
    private val propertyRepository: PropertyRepository,
    private val interactionRepository: InteractionRepository,
    private val customerRequirementsRepository: CustomerRequirementRepository,
    private val sellerRepository: SellerRepository
) : ViewModel() {

    val studentViewModel = StudentViewModel(studentRepository)
    val customerViewModel = CustomerViewModel(customerRepository)
    val propertyViewModel = PropertyViewModel(propertyRepository)
    val interactionViewModel = InteractionViewModel(interactionRepository)
    val customerRequirementsViewModel = CustomerRequirementViewModel(customerRequirementsRepository)
    val sellerViewModel = SellerViewModel(sellerRepository)
}
