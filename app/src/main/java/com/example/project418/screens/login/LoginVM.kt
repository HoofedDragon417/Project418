package com.example.project418.screens.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.project418.common.BaseVM
import com.example.project418.common.Screens
import com.example.project418.models.Department
import com.example.project418.storage.DataBaseHelper
import com.example.project418.storage.SharedPreference
import kotlinx.coroutines.launch

class LoginVM : BaseVM() {
    fun auth(user: Department, context: Context) {
        viewModelScope.launch {
            val listOfDepartments = DataBaseHelper(context).getListOfDepartments()

            for (department in listOfDepartments) {
                if (user.login == department.login && user.password == department.password) {
                    SharedPreference(context).saveID(department.id)
                    router.newRootScreen(Screens.Main())
                }
            }
        }
    }
}