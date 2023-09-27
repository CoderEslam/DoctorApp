package com.doubleclick.doctorapp.android.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.doubleclick.doctorapp.android.Model.Message
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.ActivityPatientVisitsBinding
import com.doubleclick.doctorapp.android.utils.Constants
import com.doubleclick.doctorapp.android.utils.Constants.TOKEN
import com.doubleclick.doctorapp.android.utils.UploadRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.apache.http.client.utils.CloneUtils.clone
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
                    Toast.makeText(
                        this,
                        "Saved at =>  ${binding.viewSignature.getSavePath().toString()}",
                        Toast.LENGTH_SHORT
                    ).show()

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "Please Write Something", Toast.LENGTH_SHORT).show()
            }
        }

        fun upload() {
            viewModel.storePatientVisit(
                "${Constants.BEARER}${TOKEN}",
                type = RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(),
                    intent.extras?.getString("type").toString()
                ),
                doctor_id = RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(),
                    intent.extras?.getString("doctor_id").toString()
                ),
                patient_id = RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(),
                    intent.extras?.getString("patient_id").toString()
                ),
                clinic_id = RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(),
                    intent.extras?.getString("clinic_id").toString()
                ),
                reservation_date = RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(),
                    intent.extras?.getString("reservation_date").toString()
                ),
                patient_reservation_id = RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(),
                    intent.extras?.getString("id").toString()
                ),
                diagnosis = RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(),
                    ""
                ),
                listImages
            ).observe(this@PatientVisitsActivity) {
                it.clone().enqueue(object : Callback<Message> {
                    override fun onResponse(
                        call: Call<Message>,
                        response: Response<Message>
                    ) {
                        Toast.makeText(
                            this@PatientVisitsActivity,
                            "response = ${response.body()?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Toast.makeText(
                            this@PatientVisitsActivity,
                            "onFailure = ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            };
        }

    }

    override fun onProgressUpdate(percentage: Int) {
        Log.e(TAG, "onProgressUpdate: $percentage")
    }
}