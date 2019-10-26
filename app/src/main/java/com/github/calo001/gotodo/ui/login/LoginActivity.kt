package com.github.calo001.gotodo.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.calo001.gotodo.R
import com.github.calo001.gotodo.ui.signup.SignupActivity
import com.github.calo001.gotodo.ui.task.TasksActivity
import com.github.calo001.gotodo.util.MySharedPreferences
import com.github.calo001.gotodo.util.Validator
import com.github.calo001.gotodo.util.color
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {
    lateinit var presenter: LoginPresenter
    private val validator = Validator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        validator
            .add(inputUsername)
            .add(inputPassword)

        presenter = LoginPresenterImpl(this)
        checkLoginToken()
        setupToolbar()
        setupListener()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbarLogin)
        supportActionBar?.title = resources.getString(R.string.wellcome)
    }

    private fun setupListener() {
        inputPassword.editText?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signin(v)
                true
            }
            false
        }
    }

    private fun checkLoginToken() {
        val token = MySharedPreferences.getToken()
        if(!token!!.isEmpty()) {
            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun showProgress() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressbar.visibility = View.GONE
    }

    override fun onSuccess() {
        val intent = Intent(this, TasksActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onError(code: Int, message: String) {
        Snackbar.make(loginCoordinator, message, Snackbar.LENGTH_LONG)
            .color(ContextCompat.getColor(this, R.color.colorAccent))
            .show()
    }

    fun signin(view: View) {
        val username = inputUsername.editText?.text.toString()
        val password = inputPassword.editText?.text.toString()

        if (validator.result()) presenter.login(username, password)
    }

    fun signup(view: View) {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }
}
