package com.example.project418.modules

import com.example.project418.screens.main.MainVMImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    includes(navigationModule)
    viewModel { MainVMImpl(get()) }
}