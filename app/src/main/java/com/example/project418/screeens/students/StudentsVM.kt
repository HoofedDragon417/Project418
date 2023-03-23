package com.example.project418.screens.students

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project418.models.Student
import com.example.project418.storage.DataBaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StudentsVM : ViewModel() {
    val listOfStudents = MutableStateFlow(listOf<Student>())

    fun getStudents(context: Context){
        viewModelScope.launch {
            listOfStudents.value= DataBaseHelper(context).getListOfStudents()
        }
    }
}