package com.kot104.baitapbuoi13.repository

import com.kot104.baitapbuoi13.room.Entity.StudentEntity
import com.kot104.baitapbuoi13.room.StudentsDB
import javax.inject.Inject

class Repository @Inject constructor(val studentsDB: StudentsDB) {
    suspend fun addStudentToRoom(studentEntity: StudentEntity){
        studentsDB.studentDao().addStudent(studentEntity)
    }

    fun getAllStudents() = studentsDB.studentDao().getALlStudents()

    suspend fun deleteStudentFromRoom(studentEntity: StudentEntity) {
        studentsDB.studentDao().deleteStudent(studentEntity)
    }

    suspend fun updateStudent(studentEntity: StudentEntity){
        studentsDB.studentDao().updateStudent(studentEntity)
    }
}