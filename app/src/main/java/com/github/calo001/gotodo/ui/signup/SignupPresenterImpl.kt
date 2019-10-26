package com.github.calo001.gotodo.ui.signup

class SignupPresenterImpl(private val view: SignupView) : SignupPresenter {
    private val interactor: SignupInteractor = SignupInteractorImpl(this)
    override fun signup(username: String, password: String) {
        view.showProgress()
        interactor.signup(username, password)
    }

    override fun onSuccess() {
        view.hideProgress()
        view.onSuccess()
    }

    override fun onError(code: Int, error: String) {
        view.hideProgress()
        view.onError(code, error)
    }

}