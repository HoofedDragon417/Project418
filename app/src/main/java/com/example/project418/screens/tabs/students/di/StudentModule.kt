package com.example.project418.modules

import com.example.project418.screens.tabs.students.StudentsVM
import com.example.project418.storage.di.databaseModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val studentModule = module {
    includes(databaseModule)
    viewModel { StudentsVM(get()) }
}