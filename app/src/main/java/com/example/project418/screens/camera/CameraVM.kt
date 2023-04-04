package com.example.project418.screens.camera

import androidx.lifecycle.ViewModel
import com.example.project418.R
import com.example.project418.common.AppGlobal
import com.example.project418.common.Screens
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CameraVM : ViewModel() {
    fun checkContent(result: String): Boolean {
        return if (result[result.lastIndex] == ',') {
            false
        } else {
            val checkList = result.split(",").map { it.toInt() }

            if (checkList.lastIndex == 2) {
                AppGlobal.AppRouter.navigateTo(Screens.CheckQr(result))
                true
            } else {
                false
            }
        }
    }
}