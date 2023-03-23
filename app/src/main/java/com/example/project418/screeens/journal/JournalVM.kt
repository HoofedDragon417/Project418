package com.example.project418.screens.journal

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project418.models.Journal
import com.example.project418.storage.DataBaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class JournalVM : ViewModel() {

    val recordsInJournal = MutableStateFlow(listOf<Journal>())

    fun getJournal(context: Context) {
        viewModelScope.launch {
            recordsInJournal.value=DataBaseHelper(context).getJournal()
        }
    }
}