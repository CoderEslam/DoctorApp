package com.doubleclick.doctorapp.android.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doubleclick.doctorapp.android.Home.HomeActivity
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import kotlinx.coroutines.*

class AuthActivity : AppCompatActivity() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        GlobalScope.launch(Dispatchers.Main) {
            if (!getToken().isNullOrEmpty()) {
                startActivity(Intent(this@AuthActivity, HomeActivity::class.java))
                finish()
            }
        }
    }
}