package com.example.project418.screens.subjects

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project418.models.Subject
import com.example.project418.storage.DataBaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SubjectsVM : ViewModel() {
    val listOfSubjects = MutableStateFlow(listOf<Subject>())

    fun getSubjects(context: Context) {
        viewModelScope.launch {
            listOfSubjects.value = DataBaseHelper(context).getListOfSubjects()
        }
    }
}