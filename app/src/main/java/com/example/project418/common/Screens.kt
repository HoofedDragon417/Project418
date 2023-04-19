@file:Suppress("FunctionName")

package com.example.project418.common

import com.example.project418.screens.camera.CameraFragment
import com.example.project418.screens.completeregistration.screens.coursework.CompleteCourseworkFragment
import com.example.project418.screens.completeregistration.screens.testwork.CompleteTestworkFragment
import com.example.project418.screens.createqr.CreateQrFragment
import com.example.project418.screens.login.LoginFragment
import com.example.project418.screens.main.MainFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun Main() = FragmentScreen { MainFragment() }

    fun Login() = FragmentScreen { LoginFragment() }

    fun CreateQr() = FragmentScreen { CreateQrFragment() }

    fun Camera() = FragmentScreen { CameraFragment() }

    fun Coursework(content: String) =
        FragmentScreen { CompleteCourseworkFragment.newInstance(content) }

    fun Testwork(content: String)=FragmentScreen{CompleteTestworkFragment.newInstance(content)}
}