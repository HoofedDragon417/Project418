package com.example.project418.screens.main

import com.example.project418.common.BaseVM
import com.example.project418.common.Screens
import com.example.project418.storage.UserConfig

class MainVM : BaseVM() {
    fun createQr() = router.navigateTo(Screens.CreateQr())

    fun scanQr() = router.navigateTo(Screens.Camera())

    fun logout() {
        UserConfig.clearID()
        router.newRootScreen(Screens.Login())
    }
}