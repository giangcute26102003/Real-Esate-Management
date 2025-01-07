package com.kot104.baitapbuoi13.room.Entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CustomerEntity::class,
            parentColumns = ["customerId"],
            childColumns = ["customerid"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PropertyEntity::class,
            parentColumns = ["propertyId"],
            childColumns = ["propertyid"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["customerid"]), // Index for the first foreign key
        Index(value = ["propertyid"])  // Index for the second foreign key
    ]
)
data class InteractionEntity(
    @PrimaryKey(autoGenerate = true)
    val interactionid: Int = 0,
    val customerid: Int,
    val propertyid: Int,
    val date: String, // ISO 8601 format (e.g., "2024-10-30")
    val details: String? = null,
    val createdat: String, // ISO 8601 format
    val updatedat: String  // ISO 8601 format
)
