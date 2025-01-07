package com.kot104.baitapbuoi13.room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class PropertyEntity(
    @PrimaryKey(autoGenerate = true)
    val propertyId: Int = 0,
    val AddressProperty: String,
    val propertyType: String,
    val size: Double,
    val floor: Int?,
    val imageUrl: String,
    val bedrooms: Int,
    val bathrooms: Int,
    val description: String?, // Replaced 'features' with 'description'
    val price: Double,
    val legalDocuments: String, // Added Legal documents
    val availability: String, // Added validation for "available", "unavailable", and "deposited" in code that uses this class.
    val phoneOwner: String, // Added phoneOwner
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)