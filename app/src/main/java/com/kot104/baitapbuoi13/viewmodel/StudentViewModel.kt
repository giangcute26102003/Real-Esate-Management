package com.kot104.baitapbuoi13.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kot104.baitapbuoi13.repository.Repository
import com.kot104.baitapbuoi13.room.Entity.StudentEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class StudentViewModel @Inject constructor(
    val repository: Repository): ViewModel() {

    fun addStudent(student: StudentEntity) {
        viewModelScope.launch {
            repository.addStudentToRoom(student)
        }
    }

    val students = repository.getAllStudents()

    fun deleteStudent(student: StudentEntity) {
        viewModelScope.launch {
            repository.deleteStudentFromRoom(student)
        }
    }

    fun updateStudent(student: StudentEntity) {
        viewModelScope.launch {
            repository.updateStudent(student)
        }
    }
}