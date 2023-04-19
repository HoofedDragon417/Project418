package com.example.project418.modules

import com.example.project418.screens.login.LoginVM
import com.example.project418.storage.di.databaseModule
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {
    includes(navigationModule, databaseModule)
    viewModelOf(::LoginVM)
}