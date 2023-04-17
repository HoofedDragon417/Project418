package com.example.project418.screens.main.di

import com.example.project418.modules.navigationModule
import com.example.project418.screens.main.MainVMImpl
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    includes(navigationModule)
    viewModelOf(::MainVMImpl)
}