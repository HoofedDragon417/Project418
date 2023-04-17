package com.example.project418.modules

import com.github.terrakok.cicerone.Cicerone
import org.koin.dsl.module

private val cicerone = Cicerone.create()


val navigationModule = module {
    single { cicerone }
    single { cicerone.router }
    single { cicerone.getNavigatorHolder() }
}