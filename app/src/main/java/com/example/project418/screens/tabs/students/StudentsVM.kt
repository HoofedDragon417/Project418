package com.example.project418.screens.tabs.students

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project418.models.Student
import com.example.project418.storage.DataBaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StudentsVM(private val dataBaseHelper: DataBaseHelper) : ViewModel() {
    val listOfStudents = MutableStateFlow(listOf<Student>())

    fun getStudents() {
        viewModelScope.launch {
            listOfStudents.value = dataBaseHelper.getListOfStudents()
        }
    }
}