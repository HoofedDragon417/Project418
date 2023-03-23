package com.example.project418.common

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router

open class BaseVM : ViewModel() {
    private var _router: Router? = null
    val router get() = requireNotNull(_router)

    fun setRouter(router: Router) {
        _router = router
    }
}