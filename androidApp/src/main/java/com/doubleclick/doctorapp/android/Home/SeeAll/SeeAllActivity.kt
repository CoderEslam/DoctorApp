package com.doubleclick.doctorapp.android.Home.SeeAll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.databinding.ActivitySeeAllBinding

class SeeAllActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeeAllBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeeAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}