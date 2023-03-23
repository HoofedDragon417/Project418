package com.example.project418.screens.main

import android.content.Context
import com.example.project418.common.BaseVM
import com.example.project418.common.Screens
import com.example.project418.storage.SharedPreference

class MainVM : BaseVM() {
    fun createQr() = router.navigateTo(Screens.CreateQr())

    fun scanQr() = router.navigateTo(Screens.Camera())

    fun logout(context: Context) {
        SharedPreference(context).clearID()
        router.newRootScreen(Screens.Login())
    }
}