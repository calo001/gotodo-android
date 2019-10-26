package com.github.calo001.gotodo.ui.login

import android.annotation.SuppressLint
import com.github.calo001.gotodo.repository.Repository
import com.github.calo001.gotodo.util.ApiError
import com.github.calo001.gotodo.util.MySharedPreferences

class LoginInteractorImpl(private val presenter: LoginPresenter): LoginInteractor {
    @SuppressLint("CheckResult")
    override fun login(username: String, password: String) {
        Repository.login(username, password)
            .subscribe({ result ->
                presenter.onSuccess()
                MySharedPreferences.saveToken(result.token)
            }, { error ->
                val apiError = ApiError(error)
                presenter.onError(apiError.code, apiError.message)
            })
    }
}