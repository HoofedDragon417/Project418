package com.example.project418.screens.main

import androidx.lifecycle.ViewModel
import com.example.project418.common.Screens
import com.example.project418.storage.UserConfig
import com.github.terrakok.cicerone.Router

class MainVM(private val router: Router) : ViewModel() {
    fun createQr() = router.navigateTo(Screens.CreateQr())

    fun scanQr() = router.navigateTo(Screens.Camera())

    fun logout() {
        UserConfig.clearID()
        router.newRootScreen(Screens.Login())
    }
}
