package com.example.project418.screens.login

import androidx.lifecycle.viewModelScope
import com.example.project418.common.AppGlobal
import com.example.project418.common.BaseVM
import com.example.project418.common.Screens
import com.example.project418.models.Department
import com.example.project418.storage.UserConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginVM : BaseVM() {

    val showAlertDialog = MutableStateFlow(false)

    fun authorization(user: Department) {
        viewModelScope.launch {
            val listOfDepartments = AppGlobal.DataBaseHelper.getListOfDepartments()

            for (department in listOfDepartments) {
                if (user.login == department.login && user.password == department.password) {
                    UserConfig.saveID(department.id)
                    router.newRootScreen(Screens.Main())
                    return@launch
                } else {
                    showAlertDialog.value = true
                }
            }
        }
    }

    fun dismissAlertDialog() {
        showAlertDialog.value = false
    }
}