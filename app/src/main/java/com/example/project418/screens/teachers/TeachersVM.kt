package com.example.project418.screens.teachers

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project418.models.Teachers
import com.example.project418.screens.journal.JournalVM
import com.example.project418.storage.DataBaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TeachersVM(private val dataBaseHelper: DataBaseHelper) : ViewModel() {
    val listOfTeachers = MutableStateFlow(listOf<Teachers>())

    fun getTeachers(context: Context) {
        viewModelScope.launch {
            listOfTeachers.value = dataBaseHelper.getListOfTeachers()
        }
    }

    companion object {
        fun Factory(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val db = DataBaseHelper(context)
                    return TeachersVM(db) as T
                }
            }
        }
    }
}