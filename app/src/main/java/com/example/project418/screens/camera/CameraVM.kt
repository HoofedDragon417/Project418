package com.example.project418.screens.camera

import androidx.lifecycle.ViewModel
import com.example.project418.common.Screens
import com.example.project418.storage.UserConfig
import com.github.terrakok.cicerone.Router

class CameraVM(private val router: Router) : ViewModel() {
     fun checkContent(result: String): Boolean {
        val qrContentTemplate = Regex("\\d+,\\d+,\\d;\\d+")

        return if (result.matches(qrContentTemplate)) {
            val (qrData, departmentID) = result.split(";")
            if (departmentID.toInt() == UserConfig.getID()) {
                val (student, subject, tof) = qrData.split(",").map { it.toInt() }
                if (tof == 1)
                    router.navigateTo(Screens.Coursework(qrData))
                if (tof == 2)
                    router.navigateTo(Screens.Testwork(qrData))
                false
            } else true
        } else {
            true
        }
    }
}
