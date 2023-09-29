package com.doubleclick.doctorapp.android.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.doubleclick.doctorapp.android.Model.Message
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.ActivityPatientVisitsBinding
import com.doubleclick.doctorapp.android.utils.Constants
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import com.doubleclick.doctorapp.android.utils.UploadRequestBody
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException

class PatientVisitsActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {

    private lateinit var binding: ActivityPatientVisitsBinding
    private lateinit var viewModel: MainViewModel
    val listImages: MutableList<MultipartBody.Part> = mutableListOf();
    private val TAG = "PatientVisitsActivity"
    private var TOKEN = ""

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientVisitsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]
        binding.clear.setOnClickListener {
            binding.viewSignature.clear();
        }
        GlobalScope.launch(Dispatchers.Main) {
            TOKEN = getToken().toString()
        }
        binding.done.setOnClickListener {

            if (binding.viewSignature.getTouched()) {
                try {
                    var path =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/" + System.currentTimeMillis() + ".png"
                    binding.viewSignature.save(path, true, 10)
                    val body = UploadRequestBody(File(path), "image", this@PatientVisitsActivity)
                    listImages.add(
                        MultipartBody.Part.createFormData(
                            "image[]",
                            "${System.currentTimeMillis()}.jpg",
                            body
                        )
                    )
                    upload(listImages)

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "Please Write Something", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun upload(listImages: MutableList<MultipartBody.Part>) {

        Log.e(TAG, "upload: ${intent.extras?.getString("type").toString()}")
        Log.e(TAG, "upload: ${intent.extras?.getString("doctor_id").toString()}")
        Log.e(TAG, "upload: ${intent.extras?.getString("patient_id").toString()}")
        Log.e(TAG, "upload: ${intent.extras?.getString("clinic_id").toString()}")
        Log.e(TAG, "upload: ${intent.extras?.getString("reservation_date").toString()}")
        Log.e(TAG, "upload: ${intent.extras?.getString("id").toString()}")

        viewModel.storePatientVisit(
            "${Constants.BEARER}${TOKEN}",
            type = intent.extras?.getString("type").toString()
                .toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            doctor_id = intent.extras?.getString("doctor_id").toString()
                .toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            patient_id = intent.extras?.getString("patient_id").toString()
                .toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            clinic_id = intent.extras?.getString("clinic_id").toString()
                .toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            reservation_date = intent.extras?.getString("reservation_date").toString()
                .toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            patient_reservation_id = intent.extras?.getString("id").toString()
                .toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            diagnosis = "--"
                .toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            listImages
        ).observe(this@PatientVisitsActivity) {
            it.clone().enqueue(object : Callback<Message> {
                override fun onResponse(
                    call: Call<Message>,
                    response: Response<Message>
                ) {
                    Toast.makeText(
                        this@PatientVisitsActivity,
                        "Done",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Log.e(TAG, "onFailure: = ${t.message}")
                }

            })
        };
    }

    override fun onProgressUpdate(percentage: Int) {
        Log.e(TAG, "onProgressUpdate: $percentage")
    }
}