package com.doubleclick.doctorapp.android.Home

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doubleclick.doctorapp.android.Adapters.DoctorVideoViewPagerAdapter
import com.doubleclick.doctorapp.android.Model.MedicalAdvice.MedicalAdviceModel
import com.doubleclick.doctorapp.android.databinding.ActivityDoctorVideoBinding
import kotlinx.android.synthetic.main.menu_left_drawer.*
import java.util.ArrayList

class DoctorVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorVideoBinding
    private val TAG = "DoctorVideoActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list: ArrayList<MedicalAdviceModel>? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableArrayListExtra(
                    "medicalAdviceModelList",
                    MedicalAdviceModel::class.java
                )
            } else {
                intent.getParcelableArrayListExtra("medicalAdviceModelList")
            }

        binding.videos.adapter = DoctorVideoViewPagerAdapter(list!!)

    }
}