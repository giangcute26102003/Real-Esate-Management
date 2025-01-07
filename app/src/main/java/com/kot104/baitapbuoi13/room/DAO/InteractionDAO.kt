package com.kot104.baitapbuoi13.room.DAO

import androidx.room.*
import com.kot104.baitapbuoi13.room.Entity.InteractionEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface InteractionDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInteraction(interaction: InteractionEntity)

    @Update
    suspend fun updateInteraction(interaction: InteractionEntity)

    @Delete
    suspend fun deleteInteraction(interaction: InteractionEntity)

    @Query("SELECT * FROM InteractionEntity WHERE interactionid = :id")
    suspend fun getInteractionById(id: Int): InteractionEntity?

//    @Query("SELECT * FROM InteractionEntity WHERE customerid = :customerId")
//    suspend fun getInteractionsByCustomerId(customerId: Int): List<InteractionEntity>
//
//    @Query("SELECT * FROM InteractionEntity WHERE propertyid = :propertyId")
//    suspend fun getInteractionsByPropertyId(propertyId: Int): List<InteractionEntity>
//
//    @Query("SELECT * FROM InteractionEntity WHERE date BETWEEN :startDate AND :endDate")
//    suspend fun getInteractionsByDateRange(startDate: String, endDate: String): List<InteractionEntity>

    @Query("SELECT * FROM InteractionEntity")
     fun getAllInteractions(): Flow<List<InteractionEntity>>
}
