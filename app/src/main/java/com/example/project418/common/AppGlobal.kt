package com.example.project418.common

import android.app.Application
import com.example.project418.modules.*
import com.example.project418.screens.completeregistration.di.completeRegistrationModule
import com.example.project418.screens.createqr.di.createQrModule
import com.example.project418.screens.main.di.mainModule
import com.example.project418.screens.tabs.di.tabModule
import com.example.project418.screens.tabs.subjects.di.subjectModule
import com.example.project418.screens.tabs.teachers.di.teacherModule
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
                cameraModule,
                completeRegistrationModule,
                createQrModule,
                tabModule
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