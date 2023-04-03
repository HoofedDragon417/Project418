package com.example.project418.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project418.R
import com.example.project418.storage.UserConfig
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AppCompatActivity() {

    private val cicerone = Cicerone.create()
    private val navigatorHolder get() = cicerone.getNavigatorHolder()
    private val navigator = AppNavigator(this, R.id.root_fragment_container)

    init {
        AppGlobal.attachRouter(cicerone.router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (UserConfig.getID() == -1)
            AppGlobal.AppRouter.newRootScreen(Screens.Login())
        else AppGlobal.AppRouter.newRootScreen(Screens.Main())
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