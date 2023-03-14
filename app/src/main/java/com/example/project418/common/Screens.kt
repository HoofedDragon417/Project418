package com.example.project418.common

import com.example.project418.screens.login.LoginFragment
import com.example.project418.screens.main.MainFragment
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun Main(router:Router) = FragmentScreen { MainFragment(router) }

    fun Login(router: Router) = FragmentScreen { LoginFragment(router) }
}
