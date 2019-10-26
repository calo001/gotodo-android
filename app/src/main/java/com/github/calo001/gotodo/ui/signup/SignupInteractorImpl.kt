package com.github.calo001.gotodo.ui.signup

import android.annotation.SuppressLint
import com.github.calo001.gotodo.repository.Repository
import com.github.calo001.gotodo.util.ApiError

class SignupInteractorImpl(val presenter: SignupPresenter) : SignupInteractor {
    @SuppressLint("CheckResult")
    override fun signup(username: String, password: String) {
        Repository.register(username, password)
            .subscribe({ presenter.onSuccess() },
                { error ->
                    val httpError = ApiError(error)
                    presenter.onError(httpError.code, httpError.message)
                })
    }
}