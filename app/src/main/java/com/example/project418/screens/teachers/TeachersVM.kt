package com.example.project418.screens.teachers

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project418.models.Teachers
import com.example.project418.storage.DataBaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TeachersVM : ViewModel() {
    val listOfTeachers = MutableStateFlow(listOf<Teachers>())

    fun getTeachers(context: Context) {
        viewModelScope.launch {
            listOfTeachers.value = DataBaseHelper(context).getListOfTeachers()
        }
    }
}