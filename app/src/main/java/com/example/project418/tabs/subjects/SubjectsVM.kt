package com.example.project418.tabs.subjects

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project418.models.Subject
import com.example.project418.storage.DataBaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SubjectsVM(private val dataBaseHelper: DataBaseHelper) : ViewModel() {
    val listOfSubjects = MutableStateFlow(listOf<Subject>())

    fun getSubjects() {
        viewModelScope.launch {
            listOfSubjects.value = dataBaseHelper.getListOfSubjects()
        }
    }

    companion object {
        fun Factory(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val db = DataBaseHelper(context)
                    return SubjectsVM(db) as T
                }
            }
        }
    }
}