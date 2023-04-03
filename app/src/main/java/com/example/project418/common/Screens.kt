@file:Suppress("FunctionName")

package com.example.project418.common

import com.example.project418.screens.camera.CameraFragment
import com.example.project418.screens.completeregistration.CompleteRegistrationFragment
import com.example.project418.screens.createqr.CreateQrFragment
import com.example.project418.screens.login.LoginFragment
import com.example.project418.screens.main.MainFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun Main() = FragmentScreen { MainFragment() }

    fun Login() = FragmentScreen { LoginFragment() }

    fun CreateQr() = FragmentScreen { CreateQrFragment() }

    fun Camera() = FragmentScreen { CameraFragment() }

    fun CheckQr(content: String) =
        FragmentScreen { CompleteRegistrationFragment.newInstance(content) }
}