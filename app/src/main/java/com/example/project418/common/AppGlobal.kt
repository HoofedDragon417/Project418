package com.example.project418.common

import android.app.Application
import com.example.project418.modules.cameraModule
import com.example.project418.modules.navigationModule
import com.example.project418.modules.loginModule
import com.example.project418.modules.mainModule
import com.example.project418.storage.DataBaseHelper
import com.example.project418.storage.UserConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import kotlin.properties.Delegates

class AppGlobal : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this

        UserConfig.attachContext(this)

        dataBase = DataBaseHelper(this)

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@AppGlobal)
            modules(
                navigationModule,
                loginModule,
                mainModule,
                cameraModule
            )
        }
    }


    companion object {
        private lateinit var instance: AppGlobal
        private var dataBase: DataBaseHelper by Delegates.notNull()

        val Instance: AppGlobal get() = instance
        val DataBaseHelper: DataBaseHelper get() = dataBase
    }

}