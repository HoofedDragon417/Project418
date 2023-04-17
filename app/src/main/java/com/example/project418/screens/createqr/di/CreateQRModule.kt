package com.example.project418.screens.createqr.di

import com.example.project418.screens.createqr.CreateQrVM
import com.example.project418.storage.di.databaseModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val createQrModule = module {
    includes(databaseModule)
    viewModel { CreateQrVM(get()) }
}