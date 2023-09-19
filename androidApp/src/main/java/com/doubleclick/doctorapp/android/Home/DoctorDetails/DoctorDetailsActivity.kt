package com.doubleclick.doctorapp.android.Home.DoctorDetails

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.doubleclick.doctorapp.android.Adapters.DoctorClinicAdapter
import com.doubleclick.doctorapp.android.Model.Doctor.DoctorsList
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.ActivityDoctorDetailesBinding
import com.doubleclick.doctorapp.android.utils.Constants
import com.doubleclick.doctorapp.android.utils.Constants.IMAGE_URL_USERS
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorDetailesBinding
    private lateinit var viewModel: MainViewModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorDetailesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]
        GlobalScope.launch(Dispatchers.Main) {
            binding.animationView.visibility = View.VISIBLE
            viewModel.getDoctorsInfoById(
                "${Constants.BEARER}${getToken()}",
                intent.extras?.getString("doctor_id").toString()
            )
                .observe(this@DoctorDetailsActivity) {
                    it.enqueue(object : Callback<DoctorsList> {
                        @SuppressLint("SetTextI18n")
                        override fun onResponse(
                            call: Call<DoctorsList>,
                            response: Response<DoctorsList>
                        ) {
                            val data = response.body()!!.data?.get(0)
                            binding.rvClinics.adapter = data?.clinics?.let { clinics -> DoctorClinicAdapter(clinics) }

                            Glide.with(this@DoctorDetailsActivity)
                                .load("${IMAGE_URL_USERS}${data?.user?.user_image}")
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(binding.ivProfile)

                            Glide.with(this@DoctorDetailsActivity)
                                .load("${IMAGE_URL_USERS}${data?.user?.user_image}")
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(binding.ivBanner)

                            binding.tvProfileDescription.text =
                                "${getString(R.string.General_Specialty)} : ${
                                    data?.general_specialty?.name + " \n " + "${
                                        getString(
                                            R.string.Specialization
                                        )
                                    } : " + data?.specialization?.name
                                }"
                            binding.animationView.visibility = View.GONE
                        }

                        override fun onFailure(call: Call<DoctorsList>, t: Throwable) {

                        }

                    })
                }


        }

    }
}