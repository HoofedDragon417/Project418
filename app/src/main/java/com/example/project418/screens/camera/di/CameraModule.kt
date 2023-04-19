package com.example.project418.modules

import com.example.project418.screens.camera.CameraVM
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val cameraModule = module {
    includes(navigationModule)
    viewModelOf(::CameraVM)
}