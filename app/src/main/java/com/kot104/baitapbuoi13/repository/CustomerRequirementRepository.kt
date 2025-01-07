package com.kot104.baitapbuoi13.repository
import com.kot104.baitapbuoi13.room.DAO.*;
import com.kot104.baitapbuoi13.room.Entity.*
import com.kot104.baitapbuoi13.room.StudentsDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CustomerRequirementRepository @Inject constructor(private val customerRequirementsDb: StudentsDB) {

    // Lấy tất cả nhu cầu của khách hàng
     fun getAllCustomerRequirements(): Flow<List<CustomerRequirementsEntity>> =
        customerRequirementsDb.CustomerRequirementDAO().getAllRequirements()

    // Tìm nhu cầu của khách hàng theo ID
     fun getCustomerRequirementById(customerId: Int): Flow<CustomerRequirementsEntity> =
        customerRequirementsDb.CustomerRequirementDAO().getRequirementsByCustomerId(customerId)

    // Thêm mới nhu cầu khách hàng
    suspend fun insertCustomerRequirement(requirement: CustomerRequirementsEntity) {
        customerRequirementsDb.CustomerRequirementDAO().insertRequirement(requirement)
    }

    // Cập nhật nhu cầu khách hàng
    suspend fun updateCustomerRequirement(requirement: CustomerRequirementsEntity) {
        customerRequirementsDb.CustomerRequirementDAO().updateRequirement(requirement)
    }

    // Xóa nhu cầu khách hàng
    suspend fun deleteCustomerRequirement(requirement: CustomerRequirementsEntity) {
        customerRequirementsDb.CustomerRequirementDAO().deleteRequirement(requirement)
    }
}

