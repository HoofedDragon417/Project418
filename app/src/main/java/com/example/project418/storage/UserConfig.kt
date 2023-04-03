package com.example.project418.storage

import android.content.Context
import kotlin.properties.Delegates

object UserConfig {

    private const val PREF_NAME = "users"
    private const val USER_ID = "user_id"

    private var context: Context by Delegates.notNull()

    fun attachContext(context: Context) {
        this.context = context
    }

    private val data by lazy { context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE) }

    fun getID() = data.getInt(USER_ID, -1)

    fun saveID(id: Int) = data.edit().putInt(USER_ID, id).apply()

    fun clearID() = data.edit().clear().commit()


}