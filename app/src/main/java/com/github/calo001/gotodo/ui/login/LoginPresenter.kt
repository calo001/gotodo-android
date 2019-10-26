package com.github.calo001.gotodo.ui.login

interface LoginPresenter {
    fun login(username: String, password: String)
    fun onSuccess()
    fun onError(code: Int, message: String)
}