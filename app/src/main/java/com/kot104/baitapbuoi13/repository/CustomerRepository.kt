package com.kot104.baitapbuoi13.repository

import androidx.lifecycle.LiveData
import com.kot104.baitapbuoi13.room.DAO.CustomerDAO
import com.kot104.baitapbuoi13.room.DAO.StudentDAO
import com.kot104.baitapbuoi13.room.Entity.*
import com.kot104.baitapbuoi13.room.StudentsDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomerRepository @Inject constructor( val customerdb: StudentsDB) {
//
    // Lấy danh sách tất cả khách hàng
     fun getAllCustomers() = customerdb.customerDao().getAllCustomers()
     fun SearchAllCustomers(keyword: String): Flow<List<CustomerEntity>> {
         return customerdb.customerDao().SearchAllCustomers(keyword)
     }


    // Tìm khách hàng theo ID
    fun getCustomerById(customerId: Int): Flow<CustomerEntity> = customerdb.customerDao().getCustomerById(customerId)

    // Thêm mới khách hàng
    suspend fun insertCustomer(customer: CustomerEntity) {
        customerdb.customerDao().insertCustomer(customer)
    }

    // Cập nhật thông tin khách hàng
    suspend fun updateCustomer(customer: CustomerEntity) {
        customerdb.customerDao().updateCustomer(customer)
    }

    // Xóa khách hàng
    suspend fun deleteCustomer(customer: CustomerEntity) {
        customerdb.customerDao().deleteCustomer(customer)
    }
}
