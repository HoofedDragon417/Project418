package com.example.project418.common

import androidx.lifecycle.ViewModel

open class BaseVM : ViewModel() {
    val router get() = AppGlobal.AppRouter
}