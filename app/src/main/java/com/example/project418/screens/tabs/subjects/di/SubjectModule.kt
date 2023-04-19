package com.example.project418.screens.tabs.subjects.di

import com.example.project418.screens.tabs.subjects.SubjectsVM
import com.example.project418.storage.di.databaseModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val subjectModule = module {
    includes(databaseModule)
    viewModelOf(::SubjectsVM)
}