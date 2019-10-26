package com.github.calo001.gotodo.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.calo001.gotodo.R
import com.github.calo001.gotodo.ui.login.LoginActivity

class SuccessfullRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successfull_register)
    }

    fun backToSignin(view: View) {
        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
