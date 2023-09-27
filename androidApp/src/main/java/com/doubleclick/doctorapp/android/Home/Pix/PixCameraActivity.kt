package com.doubleclick.doctorapp.android.Home.Pix

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.doubleclick.doctorapp.android.Adapters.PixAdapter
import com.doubleclick.doctorapp.android.Home.fragment.fragmentBody
import com.doubleclick.doctorapp.android.Home.options
import com.doubleclick.doctorapp.android.Model.Message
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.api.RetrofitInstance
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import com.doubleclick.doctorapp.android.utils.UploadRequestBody
import com.doubleclick.doctorapp.android.utils.getFileName
import com.iceteck.silicompressorr.SiliCompressor
import io.ak1.pix.helpers.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


private const val TAG = "PixCameraActivity"

class PixCameraActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {
    private lateinit var viewModel: MainViewModel
    private var TOKEN = ""

    val listImages: MutableList<MultipartBody.Part> = mutableListOf();

    private val resultsFragment = ResultsFragment {
        showCameraFragment()
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pix_camera)
        setupScreen()
        supportActionBar?.hide()
        showResultsFragment()

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]
        GlobalScope.launch(Dispatchers.Main) {
            TOKEN = getToken().toString()
        }
    }

    private fun showCameraFragment() {
        addPixToActivity(R.id.container_pix, options) { results ->
            when (results.status) {
                PixEventCallback.Status.SUCCESS -> {
                    showResultsFragment()
                    results.data.forEach { uri ->
                        Log.e(TAG, "showCameraFragment: ${uri.path}")
                        val parcelFileDescriptor: ParcelFileDescriptor =
                            contentResolver.openFileDescriptor(
                                uri,
                                "r",
                                null
                            )!!
                        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                        val file =
                            File(
                                cacheDir,
                                contentResolver.getFileName(uri)
                            )
                        val outputStream = FileOutputStream(file)
                        inputStream.copyTo(outputStream)
                        val body = UploadRequestBody(file, "video", this@PixCameraActivity)
                        uploadVideo(body)
                        listImages.add(
                            MultipartBody.Part.createFormData(
                                "image[]",
                                "${System.currentTimeMillis()}.jpg",
                                body
                            )
                        )
                    }


                    resultsFragment.setList(results.data)

                }
                PixEventCallback.Status.BACK_PRESSED -> {
                    supportFragmentManager.popBackStack()
                }
            }

        }
    }

    private fun showResultsFragment() {
        showStatusBar()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_pix, resultsFragment).commit()
    }

    override fun onBackPressed() {
        val f = supportFragmentManager.findFragmentById(R.id.container_pix)
        if (f is ResultsFragment)
            super.onBackPressed()
        else
            PixBus.onBackPressedEvent()
    }

    override fun onProgressUpdate(percentage: Int) {
        Log.e(TAG, "onProgressUpdate: ${percentage}")
    }

    private fun uploadVideo(body: UploadRequestBody) {
        RetrofitInstance.api.postMedicalAdvices(
            "$BEARER$TOKEN",
            MultipartBody.Part.createFormData(
                "video",
                "${System.currentTimeMillis()}.mp4",
                body
            )
        ).clone().enqueue(object : Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                Toast.makeText(
                    this@PixCameraActivity,
                    response.body()?.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {

            }

        })


    }

    fun upload() {
        viewModel.storePatientVisit(
            "$BEARER${TOKEN}",
            type = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                "Check Up F"
            ),
            doctor_id = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                "1"
            ),
            patient_id = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                "1"
            ),
            clinic_id = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                "1"
            ),
            reservation_date = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                "12-04-2024"
            ),
            patient_reservation_id = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                "1"
            ),
            diagnosis = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                "test diagnosis"
            ),
            listImages
        ).observe(this@PixCameraActivity) {
            it.clone().enqueue(object : Callback<Message> {
                override fun onResponse(
                    call: Call<Message>,
                    response: Response<Message>
                ) {
                    Toast.makeText(
                        this@PixCameraActivity,
                        "response = ${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Toast.makeText(
                        this@PixCameraActivity,
                        "onFailure = ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        };
    }

}

class ResultsFragment(private val clickCallback: View.OnClickListener) : Fragment() {
    private val customAdapter = PixAdapter()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Uri>) {
        customAdapter.apply {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = fragmentBody(requireActivity(), customAdapter, clickCallback)
}
