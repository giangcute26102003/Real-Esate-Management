package com.kot104.baitapbuoi13.repository

import com.kot104.baitapbuoi13.room.Entity.*
import com.kot104.baitapbuoi13.room.DAO.*
import com.kot104.baitapbuoi13.room.StudentsDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InteractionRepository @Inject constructor(private val interactionDb: StudentsDB) {

    // Lấy danh sách tất cả lịch sử tương tác
     fun getAllInteractions()= interactionDb.InteractionDAO().getAllInteractions()

//    // Tìm lịch sử tương tác theo ID
//    suspend fun getInteractionById(interactionId: Int): InteractionEntity? = interactionDb.InteractionDAO().getInteractionById(interactionId)

    // Thêm mới lịch sử tương tác
    suspend fun insertInteraction(interaction: InteractionEntity) {
        interactionDb.InteractionDAO().insertInteraction(interaction)
    }

    // Cập nhật thông tin tương tác
    suspend fun updateInteraction(interaction: InteractionEntity) {
        interactionDb.InteractionDAO().updateInteraction(interaction)
    }

    // Xóa lịch sử tương tác
    suspend fun deleteInteraction(interaction: InteractionEntity) {
        interactionDb.InteractionDAO().deleteInteraction(interaction)
    }
}
