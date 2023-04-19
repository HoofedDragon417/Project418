package com.example.project418.screens.completeregistration.screens.coursework.di

import com.example.project418.screens.completeregistration.screens.coursework.CompleteCourseworkVM
import com.example.project418.storage.databaseModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val completeCoursework= module {
    includes(databaseModule)
    viewModelOf(::CompleteCourseworkVM)
}