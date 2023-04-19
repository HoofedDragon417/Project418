package com.example.project418.screens.completeregistration.screens.testwork

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project418.R
import com.example.project418.common.AppGlobal
import com.example.project418.models.Teachers
import com.example.project418.storage.DataBaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class CompleteTestworkVM(private val dataBaseHelper: DataBaseHelper) : ViewModel() {

    val student = MutableStateFlow("")
    val subject = MutableStateFlow("")
    val typeOfWork = MutableStateFlow("")

    val stringListOfTeachers = MutableStateFlow(listOf<String>())

    private var listOfTeachers = listOf<Teachers>()
    private var teacherPosition = -1

    private var studentID = -1
    private var subjectID = -1


    val errorTeacherMessage = MutableStateFlow<String?>(null)
    val errorInsertMessage = MutableStateFlow(false)

    fun getQrContent(qrContent: String) {
        viewModelScope.launch {
            val (studentID, subjectID, typeOfWorkID) = qrContent.split(",").map { it.toInt() }

            this@CompleteTestworkVM.studentID = studentID
            this@CompleteTestworkVM.subjectID = subjectID

            student.value = dataBaseHelper.getStudent(studentID)
            subject.value = dataBaseHelper.getSubject(subjectID)
            typeOfWork.value = dataBaseHelper.getTypeOfWork(typeOfWorkID)

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

    fun registrationWork() {
        if (teacherPosition == -1)
            errorTeacherMessage.value = AppGlobal.Instance.getString(R.string.choose_teacher)
        else {
            val registrationDate = Calendar.getInstance().timeInMillis
            val result = dataBaseHelper.registerTest(
                studentID,
                subjectID,
                listOfTeachers[teacherPosition].id,
                registrationDate
            )
            if (result == -1L) errorInsertMessage.value = true
        }
    }

    fun errorNull() {
        viewModelScope.launch {
            errorTeacherMessage.value = null
        }
    }
}