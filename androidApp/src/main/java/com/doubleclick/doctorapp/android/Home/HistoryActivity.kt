package com.doubleclick.doctorapp.android.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.doubleclick.doctorapp.android.Model.PatientReservations.PatientOldReservation.MyPatientReservation
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.ActivityHistoryBinding
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.SessionManger.getId
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var viewModel: MainViewModel
    private val TAG = "HistoryActivity"

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]

        GlobalScope.launch(Dispatchers.Main) {

            viewModel.getHistoryPatientVisitDoctorList(
                token = BEARER + getToken().toString(),
                id = getId().toString()
            ).observe(this@HistoryActivity) {
                it.clone().enqueue(object : Callback<MyPatientReservation> {
                    override fun onResponse(
                        call: Call<MyPatientReservation>,
                        response: Response<MyPatientReservation>
                    ) {
                        Log.e(TAG, "onResponse: ${response.body()?.data.toString()}")
                    }

                    override fun onFailure(call: Call<MyPatientReservation>, t: Throwable) {
                    }
                })
            }

        }

    }

}