package com.example.project418.storage

import android.content.Context

class SharedPreference(ctx: Context) {
    private val data = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getID() = data.getInt(USER_ID, -1)

    fun saveID(id: Int) = data.edit().putInt(USER_ID, id).apply()

    fun clearID() = data.edit().clear().commit()

    companion object {
        private const val PREF_NAME = "users"
        private const val USER_ID = "user_id"
    }
}