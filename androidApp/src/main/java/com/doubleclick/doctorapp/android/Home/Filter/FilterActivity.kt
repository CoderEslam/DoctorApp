package com.doubleclick.doctorapp.android.Home.Filter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.doubleclick.doctorapp.android.databinding.ActivityFilterBinding
import com.doubleclick.doctorapp.android.views.SeekArc.SeekArc
import com.doubleclick.doctorapp.android.views.SeekArc.`interface`.OnSeekArcChangeListener

class FilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}