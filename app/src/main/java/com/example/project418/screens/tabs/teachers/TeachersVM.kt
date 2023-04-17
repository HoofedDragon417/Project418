package com.example.project418.screens.tabs.teachers

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project418.models.Teachers
import com.example.project418.storage.DataBaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TeachersVM(private val dataBaseHelper: DataBaseHelper) : ViewModel() {
    val listOfTeachers = MutableStateFlow(listOf<Teachers>())

    fun getTeachers() {
        viewModelScope.launch {
            listOfTeachers.value = dataBaseHelper.getListOfTeachers()
        }
    }
}