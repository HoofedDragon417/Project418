package com.example.project418.screens.camera

import androidx.lifecycle.ViewModel
import com.example.project418.common.Screens
import com.example.project418.storage.UserConfig
import com.github.terrakok.cicerone.Router

class CameraVMImpl(private val router: Router) : ViewModel(), CameraVM {
    override fun checkContent(result: String): Boolean {
        val qrContentTemplate = Regex("\\d+,\\d+,\\d;\\d+")

        return if (result.matches(qrContentTemplate)) {
            val (qrData, departmentID) = result.split(";")
            if (departmentID.toInt() == UserConfig.getID()) {
                router.navigateTo(Screens.CheckQr(qrData))
                false
            } else true
        } else {
            true
        }
    }
}

interface CameraVM {
    fun checkContent(result: String): Boolean
}
