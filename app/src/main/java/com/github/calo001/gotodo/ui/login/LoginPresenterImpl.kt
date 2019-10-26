package com.github.calo001.gotodo.ui.login

class LoginPresenterImpl(val view: LoginView) : LoginPresenter {

    var interactor: LoginInteractor = LoginInteractorImpl(this)

    override fun login(username: String, password: String) {
        view.showProgress()
        interactor.login(username, password)
    }

    override fun onSuccess() {
        view.hideProgress()
        view.onSuccess()
    }

    override fun onError(code: Int, message: String) {
        view.hideProgress()
        view.onError(code, message)
    }
}