package com.example.project418.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project418.common.Screens
import com.example.project418.models.Department
import com.example.project418.storage.DataBaseHelper
import com.example.project418.storage.UserConfig
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginVM(private val router: Router, private val dataBaseHelper: DataBaseHelper) :
    ViewModel() {

    val showAlertDialog = MutableStateFlow(false)

    fun authorization(user: Department) {
        viewModelScope.launch {
            val departmentID = dataBaseHelper.singIn(user)

            if (departmentID != FALSE_USER) {
                UserConfig.saveID(departmentID)
                router.navigateTo(Screens.Main())
            } else showAlertDialog.value = true
        }
    }

    fun dismissAlertDialog() {
        showAlertDialog.value = false
    }

    companion object {
        private const val FALSE_USER = -1
    }
}
