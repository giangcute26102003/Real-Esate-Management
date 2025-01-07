package com.kot104.baitapbuoi13.room.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kot104.baitapbuoi13.room.Entity.CustomerEntity
import com.kot104.baitapbuoi13.room.Entity.CustomerRequirementsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: CustomerEntity)



    @Update
    suspend fun updateCustomer(customer: CustomerEntity)

    @Delete
    suspend fun deleteCustomer(customer: CustomerEntity)

    @Query("SELECT * FROM CustomerEntity  WHERE customerId = :customerId")
    fun getCustomerById(customerId: Int): Flow<CustomerEntity>

    @Query("SELECT * FROM CustomerEntity ORDER BY name ASC")
    fun getAllCustomers(): Flow<List<CustomerEntity>>

    @Query("SELECT * FROM CustomerEntity" +
            "    WHERE name LIKE '%' || :keyword || '%' " +
            "    OR phoneNumber LIKE '%' || :keyword || '%'  " +
            "    OR email LIKE '%' || :keyword || '%'  LIKE '%' || :keyword || '%'")
    fun SearchAllCustomers(keyword: String): Flow<List<CustomerEntity>>

}
