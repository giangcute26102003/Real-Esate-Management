package com.kot104.baitapbuoi13.room.DAO

import androidx.room.*
import com.kot104.baitapbuoi13.room.Entity.SellerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SellerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeller(seller : SellerEntity)

    @Update
    suspend fun updateSeller(seller : SellerEntity)

    @Delete
    suspend fun deleteSeller(seller : SellerEntity)

    @Query("SELECT * FROM SellerEntity limit 1")
     fun getAllSeller(): Flow<SellerEntity>
}