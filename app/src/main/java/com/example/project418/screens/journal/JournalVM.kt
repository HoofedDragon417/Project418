package com.example.project418.screens.journal

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project418.models.Journal
import com.example.project418.storage.DataBaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class JournalVM(private val dataBaseHelper: DataBaseHelper) : ViewModel() {

    val recordsInJournal = MutableStateFlow(listOf<Journal>())

    fun getJournal() {
        viewModelScope.launch {
            recordsInJournal.value=dataBaseHelper.getJournal()
        }
    }

    companion object {
        fun Factory(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val db = DataBaseHelper(context)
                    return JournalVM(db) as T
                }
            }
        }
    }
}