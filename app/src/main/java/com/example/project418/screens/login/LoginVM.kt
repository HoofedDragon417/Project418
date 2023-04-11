package com.example.project418.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project418.common.AppGlobal
import com.example.project418.common.Screens
import com.example.project418.models.Department
import com.example.project418.storage.UserConfig
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginVMImpl(private val router: Router) : ViewModel(), LoginVM {

    val showAlertDialog = MutableStateFlow(false)

    override fun authorization(user: Department) {
        viewModelScope.launch {
            val departmentID = AppGlobal.DataBaseHelper.singIn(user)

            if (departmentID != FALSE_USER) {
                UserConfig.saveID(departmentID)
                router.navigateTo(Screens.Main())
            } else showAlertDialog.value = true
        }
    }

    override fun dismissAlertDialog() {
        showAlertDialog.value = false
    }

    companion object {
        private const val FALSE_USER = -1
    }
}

interface LoginVM {
    fun authorization(user: Department)
    fun dismissAlertDialog()
}
