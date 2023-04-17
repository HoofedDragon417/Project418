package com.example.project418.modules

import com.example.project418.screens.tabs.journal.JournalVM
import com.example.project418.storage.di.databaseModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val journalModule = module {
    includes(databaseModule)
    viewModel { JournalVM(get()) }
}