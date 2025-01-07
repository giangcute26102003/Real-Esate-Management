package com.kot104.baitapbuoi13.room.DAO


import androidx.room.*
import com.kot104.baitapbuoi13.room.Entity.CustomerRequirementsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerRequirementDAO {
    // Thêm mới một nhu cầu khách hàng
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequirement(requirement: CustomerRequirementsEntity)

    // Cập nhật nhu cầu khách hàng
    @Update
    suspend fun updateRequirement(requirement: CustomerRequirementsEntity)

    // Xóa một nhu cầu khách hàng
    @Delete
    suspend fun deleteRequirement(requirement: CustomerRequirementsEntity)

    // Lấy nhu cầu của khách hàng dựa trên ID nhu cầu
    @Query("SELECT * FROM CustomerRequirementsEntity WHERE requirementId = :requirementId")
    suspend fun getRequirementById(requirementId: Int): CustomerRequirementsEntity?

    // Lấy danh sách nhu cầu dựa trên ID khách hàng
    @Query("SELECT * FROM CustomerRequirementsEntity WHERE customerId = :customerId")
     fun getRequirementsByCustomerId(customerId: Int): Flow<CustomerRequirementsEntity>

    // Lấy danh sách nhu cầu dựa trên loại bất động sản
    @Query("SELECT * FROM CustomerRequirementsEntity WHERE propertyType = :propertyType")
    suspend fun getRequirementsByPropertyType(propertyType: String): List<CustomerRequirementsEntity>

    // Tìm nhu cầu trong một khoảng ngân sách
    @Query("SELECT * FROM CustomerRequirementsEntity WHERE budgetMax >= :minBudget AND budgetMax <= :maxBudget")
    suspend fun getRequirementsByBudgetRange(minBudget: Double, maxBudget: Double): List<CustomerRequirementsEntity>

    // Lấy tất cả các nhu cầu
    @Query("SELECT * FROM CustomerRequirementsEntity")
     fun getAllRequirements(): Flow<List<CustomerRequirementsEntity>>
}