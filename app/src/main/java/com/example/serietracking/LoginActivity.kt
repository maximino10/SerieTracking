package com.example.serietracking

import android.os.Bundle
import android.view.View
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    fun executeLogin(view: View) {
        val intent = Intent(this@LoginActivity, LoginWebViewActivity::class.java)
        startActivity(intent)
    }
}