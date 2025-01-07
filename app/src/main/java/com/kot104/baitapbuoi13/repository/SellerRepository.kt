package com.kot104.baitapbuoi13.repository

import com.kot104.baitapbuoi13.room.Entity.*
import com.kot104.baitapbuoi13.room.DAO.*
import com.kot104.baitapbuoi13.room.StudentsDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SellerRepository @Inject constructor(private val sellerDb: StudentsDB) {

    // Lấy tất cả người bán
     fun getAllSellers(): Flow<SellerEntity> = sellerDb.SellerDAO().getAllSeller()


    // Thêm mới người bán
    suspend fun insertSeller(seller: SellerEntity) {
        sellerDb.SellerDAO().insertSeller(seller)
    }

    // Cập nhật thông tin người bán
    suspend fun updateSeller(seller: SellerEntity) {
        sellerDb.SellerDAO().updateSeller(seller)
    }

    // Xóa người bán
    suspend fun deleteSeller(seller: SellerEntity) {
        sellerDb.SellerDAO().deleteSeller(seller)
    }
}