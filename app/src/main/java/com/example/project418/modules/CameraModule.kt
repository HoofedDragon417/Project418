package com.example.project418.modules

import com.example.project418.screens.camera.CameraVMImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cameraModule = module {
    includes(navigationModule)
    viewModel { CameraVMImpl(get()) }
}