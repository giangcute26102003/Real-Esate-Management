package com.kot104.baitapbuoi13.repository

import com.kot104.baitapbuoi13.room.Entity.*
import com.kot104.baitapbuoi13.room.DAO.*
import com.kot104.baitapbuoi13.room.StudentsDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PropertyRepository @Inject constructor(private val propertyDb: StudentsDB) {

    // Lấy danh sách tất cả tài sản
     fun getAllProperties() = propertyDb.PropertyDao().getAllProperties()

    // Tìm tài sản theo ID
     fun getPropertyById(propertyId: Int): Flow<PropertyEntity> = propertyDb.PropertyDao().getPropertyById(propertyId)

    // Thêm mới tài sản
    suspend fun insertProperty(property: PropertyEntity) {
        propertyDb.PropertyDao().insertProperty(property)
    }

    // Cập nhật thông tin tài sản
    suspend fun updateProperty(property: PropertyEntity) {
        propertyDb.PropertyDao().updateProperty(property)
    }

    // Xóa tài sản
    suspend fun deleteProperty(property: PropertyEntity) {
        propertyDb.PropertyDao().deleteProperty(property)
    }
}
