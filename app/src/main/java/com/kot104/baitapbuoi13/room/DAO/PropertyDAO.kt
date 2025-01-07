package com.kot104.baitapbuoi13.room.DAO

import androidx.room.*
import com.kot104.baitapbuoi13.room.Entity.PropertyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProperty(property: PropertyEntity)

    @Update
    suspend fun updateProperty(property: PropertyEntity)

    @Delete
    suspend fun deleteProperty(property: PropertyEntity)

    @Query("SELECT * FROM PropertyEntity WHERE propertyId = :id")
     fun getPropertyById(id: Int): Flow<PropertyEntity>

    @Query("SELECT * FROM PropertyEntity")
    fun getAllProperties(): Flow<List<PropertyEntity>>
//    @Query("SELECT * FROM PropertyEntity WHERE availability = :availabilityStatus")
//    suspend fun getPropertiesByAvailability(availabilityStatus: String): Flow<List<PropertyEntity>>
//
//    @Query("SELECT * FROM PropertyEntity WHERE price BETWEEN :minPrice AND :maxPrice")
//    suspend fun getPropertiesByPriceRange(minPrice: Double, maxPrice: Double): Flow<List<PropertyEntity>>


}
