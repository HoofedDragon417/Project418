package com.example.project418.modules

import com.example.project418.screens.login.LoginVMImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    includes(navigationModule)
    viewModel { LoginVMImpl(get()) }
}