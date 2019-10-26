package com.github.calo001.gotodo.ui.signup

interface SignupPresenter {
    fun signup(username: String, password: String)
    fun onSuccess()
    fun onError(code: Int, error: String)
}