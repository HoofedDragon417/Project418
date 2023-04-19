package com.example.project418.screens.completeregistration.screens.testwork.di

import com.example.project418.screens.completeregistration.screens.testwork.CompleteTestworkVM
import com.example.project418.storage.databaseModule
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val completeTestwork = module {
    includes(databaseModule)
    viewModelOf(::CompleteTestworkVM)
}