package com.github.calo001.gotodo.ui.login

interface LoginView {
    fun showProgress()
    fun hideProgress()
    fun onSuccess()
    fun onError(code: Int, message: String)
}