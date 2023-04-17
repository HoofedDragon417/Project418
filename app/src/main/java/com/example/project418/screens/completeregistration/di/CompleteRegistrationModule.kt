package com.example.project418.screens.completeregistration.di

import com.example.project418.storage.di.databaseModule
import com.example.project418.screens.completeregistration.CompleteRegistrationVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val completeRegistrationModule = module {
    includes(databaseModule)
    viewModel { CompleteRegistrationVM(get()) }
}