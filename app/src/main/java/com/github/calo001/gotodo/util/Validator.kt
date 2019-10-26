package com.github.calo001.gotodo.util

import com.github.calo001.gotodo.GoTodo
import com.github.calo001.gotodo.R
import com.google.android.material.textfield.TextInputLayout

class Validator {
    var validations = mutableListOf<TextInputLayout>()

    fun add(value: TextInputLayout): Validator{
        validations.add(value)
        return this
    }
    private fun validateForNotEmpty(): Boolean {
        val check = validations.filter {
            it.editText?.text.isNullOrEmpty()
        }

        check.map {
            it.error = GoTodo.applicationContext().resources.getString(R.string.error_empty)
        }

        return check.isEmpty()
    }

    fun result(): Boolean {
        validations.map { it.error = null }
        return validateForNotEmpty()
    }

}