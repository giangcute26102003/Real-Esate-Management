package com.kot104.baitapbuoi13.room.Entity

import androidx.room.*
import java.util.Date

@Entity
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true)
    val customerId: Int = 0,
    val name: String,
    val phoneNumber: String?,
    val email: String? = null,
    val address: String? = null,
    val dob: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
