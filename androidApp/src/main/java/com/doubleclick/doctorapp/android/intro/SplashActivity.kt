package com.doubleclick.doctorapp.android.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doubleclick.doctorapp.android.Auth.AuthActivity
import com.doubleclick.doctorapp.android.MainActivity
import com.doubleclick.doctorapp.android.databinding.ActivitySplashBinding
import java.util.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                finish()
            }
        }, 2000)
    }
}