package com.github.calo001.gotodo

import android.app.Application
import android.content.Context

class GoTodo : Application() {
    init {
       instance = this
    }

    companion object {
        private var instance: GoTodo? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}