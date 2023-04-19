package com.example.project418.screens.tabs.teachers.di

import com.example.project418.screens.tabs.teachers.TeachersVM
import com.example.project418.storage.di.databaseModule
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val teacherModule = module {
    includes(databaseModule)
    viewModelOf(::TeachersVM)
}