package com.example.project418.screens.completeregistration

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project418.common.BaseVM
import com.example.project418.models.Teachers
import com.example.project418.storage.DataBaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class CompleteRegistrationVM(private val dataBaseHelper: DataBaseHelper) : BaseVM() {
    val student = MutableStateFlow("")
    val subject = MutableStateFlow("")
    val typeOfWork = MutableStateFlow("")
    val titleEnable = MutableStateFlow(true)

    val stringListOfTeachers = MutableStateFlow(listOf<String>())

    private var listOfTeachers = listOf<Teachers>()
    private var teacherPosition = -1

    private var studentID = -1
    private var subjectID = -1


    val errorMessage = MutableStateFlow<String?>(null)

    fun getQrContent(qrContent: String) {
        viewModelScope.launch {
            val (studentID, subjectID, typeOfWorkID) = qrContent.split(",").map { it.toInt() }

            this@CompleteRegistrationVM.studentID = studentID
            this@CompleteRegistrationVM.subjectID = subjectID

            student.value = dataBaseHelper.getStudent(studentID)
            subject.value = dataBaseHelper.getSubject(subjectID)
            typeOfWork.value = dataBaseHelper.getTypeOfWork(typeOfWorkID)
            if (typeOfWorkID == 2) {
                titleEnable.value = false
            }

            val listTeachers = mutableListOf<String>()
            listOfTeachers = dataBaseHelper.getListOfTeachers()

            for (teacher in listOfTeachers)
                listTeachers.add("${teacher.lastName} ${teacher.firstName} ${teacher.middleName}")
            stringListOfTeachers.value = listTeachers
        }
    }

    fun getTeacherPosition(teacher: String) {
        viewModelScope.launch {
            teacherPosition = stringListOfTeachers.value.indexOf(teacher)
        }
    }

    fun registrationWork(titleOfWork: String) {
        viewModelScope.launch {
            if (teacherPosition == -1)
                errorMessage.value = "Choose teacher"
            else {
                val registrationDate = Calendar.getInstance().timeInMillis
                if (titleOfWork.isEmpty()) {
                    dataBaseHelper.registerTest(
                        studentID,
                        subjectID,
                        listOfTeachers[teacherPosition].id,
                        registrationDate
                    )
                } else {
                    dataBaseHelper.registerCourseWork(
                        studentID,
                        subjectID,
                        listOfTeachers[teacherPosition].id,
                        titleOfWork,
                        registrationDate
                    )
                }
            }
        }
    }

    fun errorNull() {
        viewModelScope.launch {
            errorMessage.value = null
        }
    }

    companion object {
        fun Factory(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val db = DataBaseHelper(context)
                    return CompleteRegistrationVM(db) as T
                }
            }
        }
    }
}