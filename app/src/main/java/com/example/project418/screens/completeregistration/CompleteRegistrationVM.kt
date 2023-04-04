package com.example.project418.screens.completeregistration

import androidx.lifecycle.viewModelScope
import com.example.project418.R
import com.example.project418.common.AppGlobal
import com.example.project418.common.BaseVM
import com.example.project418.models.Teachers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class CompleteRegistrationVM : BaseVM() {
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

            student.value = AppGlobal.DataBaseHelper.getStudent(studentID)
            subject.value = AppGlobal.DataBaseHelper.getSubject(subjectID)
            typeOfWork.value = AppGlobal.DataBaseHelper.getTypeOfWork(typeOfWorkID)
            if (typeOfWorkID == 2) {
                titleEnable.value = false
            }

            val listTeachers = mutableListOf<String>()
            listOfTeachers = AppGlobal.DataBaseHelper.getListOfTeachers()

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
                errorMessage.value = AppGlobal.Instance.getString(R.string.choose_teacher)
            else {
                val registrationDate = Calendar.getInstance().timeInMillis
                if (titleOfWork.isEmpty()) {
                    AppGlobal.DataBaseHelper.registerTest(
                        studentID,
                        subjectID,
                        listOfTeachers[teacherPosition].id,
                        registrationDate
                    )
                } else {
                    AppGlobal.DataBaseHelper.registerCourseWork(
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
}