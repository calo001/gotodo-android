package com.github.calo001.gotodo.ui.signup

interface SignupView {
    fun showProgress()
    fun hideProgress()
    fun onSuccess()
    fun onError(code: Int, message: String)
}