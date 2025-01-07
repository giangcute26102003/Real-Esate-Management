package com.kot104.baitapbuoi13.room.Entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    foreignKeys = [ForeignKey(
        entity = CustomerEntity::class,
        parentColumns = ["customerId"],
        childColumns = ["customerId"],
        onDelete = ForeignKey.CASCADE
    )
    ],
            indices = [Index(value = ["customerId"])]
)
data class CustomerRequirementsEntity(
    @PrimaryKey(autoGenerate = true)
    val requirementId: Int = 0,
    val customerId: Int,
    val purpose: String? = null,
    val budgetMin: Double?,
    val budgetMax: Double?,
    val preferredLocation: String?,
    val propertyType: String?,
    val sizeMin: Double?,
    val bedrooms: Int?,
    val bathrooms: Int?,
    val otherPreferences: String?,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
