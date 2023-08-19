package com.doubleclick.doctorapp.android.Home.DoctorConfig

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doubleclick.doctorapp.android.databinding.ActivityDoctorConfigBinding

class DoctorConfigActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorConfigBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}