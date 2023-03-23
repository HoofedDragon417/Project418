package com.example.project418.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project418.R
import com.example.project418.storage.SharedPreference
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AppCompatActivity() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    private val navigatorHolder get() = cicerone.getNavigatorHolder()
    private val navigator = AppNavigator(this, R.id.root_fragment_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (SharedPreference(this).getID() == -1)
            router.newRootScreen(Screens.Login())
        else router.newRootScreen(Screens.Main())
    }

    override fun onResumeFragments() {
        navigatorHolder.setNavigator(navigator)
        super.onResumeFragments()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}