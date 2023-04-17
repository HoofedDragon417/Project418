package com.example.project418.storage.di

import com.example.project418.modules.navigationModule
import com.example.project418.storage.DataBaseHelper
import org.koin.dsl.module

val databaseModule = module {
    includes(navigationModule)
    single { DataBaseHelper(get()) }
}