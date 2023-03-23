package com.example.project418.screens.completeregistration

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.project418.common.BaseVM
import com.example.project418.models.Teachers
import com.example.project418.storage.DataBaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class CompleteRegistrationVM : BaseVM() {
    val student = MutableStateFlow("")
    val subject = MutableStateFlow("")
    val typeOfWork = MutableStateFlow("")

    val stringListOfTeachers = MutableStateFlow(listOf<String>())

    private var listOfTeachers = listOf<Teachers>()
    private var teacherPosition = -1

    private var studentID = -1
    private var subjectID = -1


    val errorMessage = MutableStateFlow<String?>(null)

    fun getQrContent(qrContent: String, context: Context) {
        viewModelScope.launch {
            val (studentID, subjectID, typeOfWorkID) = qrContent.split(",").map { it.toInt() }

            this@CompleteRegistrationVM.studentID = studentID
            this@CompleteRegistrationVM.subjectID = subjectID

            student.value = DataBaseHelper(context).getStudent(studentID)
            subject.value = DataBaseHelper(context).getSubject(subjectID)
            typeOfWork.value = DataBaseHelper(context).getTypeOfWork(typeOfWorkID)

            val listTeachers = mutableListOf<String>()
            listOfTeachers = DataBaseHelper(context).getListOfTeachers()

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

    fun registrationWork(titleOfWork: String, context: Context) {
        viewModelScope.launch {
            if (teacherPosition == -1)
                errorMessage.value = "Choose teacher"
            else {
                val registrationDate = Calendar.getInstance().timeInMillis
                if (titleOfWork.isEmpty()) {
                    val res = DataBaseHelper(context).registerTest(
                        studentID,
                        subjectID,
                        listOfTeachers[teacherPosition].id,
                        registrationDate
                    )
                    Toast.makeText(context, res.toString(), Toast.LENGTH_SHORT).show()
                } else {
                    val res = DataBaseHelper(context).registerCourseWork(
                        studentID,
                        subjectID,
                        listOfTeachers[teacherPosition].id,
                        titleOfWork,
                        registrationDate
                    )
                    Toast.makeText(context, res.toString(), Toast.LENGTH_SHORT).show()
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