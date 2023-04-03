package com.example.project418.common

import android.app.Application
import com.example.project418.storage.DataBaseHelper
import com.example.project418.storage.UserConfig
import com.github.terrakok.cicerone.Router
import kotlin.properties.Delegates

class AppGlobal : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this

        UserConfig.attachContext(this)

        dataBase = DataBaseHelper(this)
    }


    companion object {
        private lateinit var instance: AppGlobal
        private var dataBase: DataBaseHelper by Delegates.notNull()
        private var router: Router by Delegates.notNull()

        val Instance: AppGlobal get() = instance
        val DataBaseHelper: DataBaseHelper get() = dataBase
        val AppRouter: Router get() = router

        fun attachRouter(router: Router) {
            this.router = router
        }
    }

}