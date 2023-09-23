package com.doubleclick.doctorapp.android.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.databinding.ActivityPatientVisitsBinding
import java.io.IOException

class PatientVisitsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientVisitsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientVisitsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.done.setOnClickListener {

            if (binding.viewSignature.getTouched()) {
                try {
                    binding.viewSignature.save(
                        Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS
                        ).absolutePath + "/" + System.currentTimeMillis() + ".png", true, 10
                    )
                    Toast.makeText(
                        this,
                        "Saved at =>  ${binding.viewSignature.getSavePath().toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "Please sign first", Toast.LENGTH_SHORT).show()
            }
        }

    }
}