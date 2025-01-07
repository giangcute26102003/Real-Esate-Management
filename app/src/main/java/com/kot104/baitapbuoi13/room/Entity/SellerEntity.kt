package com.kot104.baitapbuoi13.room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SellerEntity(
    @PrimaryKey(autoGenerate = true)
    val sellerid: Int = 0, // Auto-increment ID
    val name: String, // Saler name
    val phonenumber: String, // Contact phone number
    val email: String, // Unique email
    val address: String? = null, // Address (nullable)
    val dob: String? = null, // Date of birth (ISO 8601 format)
    val nationality: String? = null, //
    val createdat: String, // ISO 8601 creation date
    val updatedat: String  // ISO 8601 update date
)
