package com.example.project418.tabs.journal

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project418.common.AppGlobal
import com.example.project418.models.Journal
import com.example.project418.storage.DataBaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class JournalVM : ViewModel() {

    val recordsInJournal = MutableStateFlow(listOf<Journal>())

    fun getJournal() {
        viewModelScope.launch {
            recordsInJournal.value = AppGlobal.DataBaseHelper.getJournal()
        }
    }
}
