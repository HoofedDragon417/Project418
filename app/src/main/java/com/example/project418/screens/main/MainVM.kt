package com.example.project418.screens.main

import androidx.lifecycle.ViewModel
import com.example.project418.common.Screens
import com.example.project418.storage.UserConfig
import com.github.terrakok.cicerone.Router

class MainVMImpl(private val router: Router) : ViewModel(), MainVM {
    override fun createQr() = router.navigateTo(Screens.CreateQr())

    override fun scanQr() = router.navigateTo(Screens.Camera())

    override fun logout() {
        UserConfig.clearID()
        router.newRootScreen(Screens.Login())
    }
}

interface MainVM {
    fun createQr()
    fun scanQr()
    fun logout()
}
