package com.github.calo001.gotodo.util

import android.content.Context
import android.content.SharedPreferences
import com.github.calo001.gotodo.GoTodo

object MySharedPreferences {
    private val sharedPreferences: SharedPreferences =
        GoTodo.applicationContext()
            .getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)

    fun getToken(): String? =
        sharedPreferences.getString(SP_TOKEN, "")


    fun saveToken(token: String) =
        sharedPreferences.edit().putString(SP_TOKEN, token).apply()

    fun clearToken() =
        sharedPreferences.edit().putString(SP_TOKEN, "").apply()
}