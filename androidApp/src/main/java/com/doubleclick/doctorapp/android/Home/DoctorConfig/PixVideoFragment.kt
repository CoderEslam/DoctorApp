package com.doubleclick.doctorapp.android.Home.DoctorConfig

import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.doubleclick.doctorapp.android.Home.options
import com.doubleclick.doctorapp.android.Model.Message
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.api.RetrofitInstance
import com.doubleclick.doctorapp.android.databinding.FragmentPixVideoBinding
import com.doubleclick.doctorapp.android.utils.Constants
import com.doubleclick.doctorapp.android.utils.Constants.TOKEN
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import com.doubleclick.doctorapp.android.utils.UploadRequestBody
import com.doubleclick.doctorapp.android.utils.getFileName
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.pixFragment
import io.ak1.pix.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

val optionsVideo = Options().apply {
    ratio = Ratio.RATIO_AUTO                                    //Image/video capture ratio
    count =
        1                                                   //Number of images to restrict selection count
    spanCount = 4                                               //Number for columns in grid
    path = "Pix/Camera"                                         //Custom Path For media Storage
    isFrontFacing = false
    videoOptions = VideoOptions().apply {
        videoDurationLimitInSeconds = 30 //Front Facing camera on start
    }
    mode =
        Mode.Video                                             //Option to select only pictures or videos or both
    flash = Flash.Auto                                          //Option to select flash type
    preSelectedUrls = ArrayList<Uri>()                          //Pre selected Image Urls
}


class PixVideoFragment : Fragment() , UploadRequestBody.UploadCallback {

    private val TAG = "PixVideoFragment"
    private var TOKEN = ""
    private lateinit var viewModel: MainViewModel
    val listImages: MutableList<MultipartBody.Part> = mutableListOf();


    private lateinit var binding: FragmentPixVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPixVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            TOKEN = requireActivity().getToken().toString()
        }
        val fragment: Fragment = pixFragment(optionsVideo) { results ->
            when (results.status) {
                PixEventCallback.Status.SUCCESS -> {
                    results.data.forEach { uri ->
                        Log.e(TAG, "showCameraFragment: ${uri.path}")
                        val parcelFileDescriptor: ParcelFileDescriptor =
                            requireActivity().contentResolver.openFileDescriptor(
                                uri,
                                "r",
                                null
                            )!!
                        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                        val file =
                            File(
                                requireActivity().cacheDir,
                                requireActivity().contentResolver.getFileName(uri)
                            )
                        val outputStream = FileOutputStream(file)
                        inputStream.copyTo(outputStream)
                        val body = UploadRequestBody(file, "video", this@PixVideoFragment)
                        uploadVideo(body)
                        listImages.add(
                            MultipartBody.Part.createFormData(
                                "image[]",
                                "${System.currentTimeMillis()}.jpg",
                                body
                            )
                        )
                    }
                }//use results as it.data

                PixEventCallback.Status.BACK_PRESSED -> {

                }// back pressed called

            }
        }


        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_video, fragment)
            .commit()


    }

    private fun uploadVideo(body: UploadRequestBody) {
        RetrofitInstance.api.postMedicalAdvices(
            "${Constants.BEARER}$TOKEN",
            MultipartBody.Part.createFormData(
                "video",
                "${System.currentTimeMillis()}.mp4",
                body
            )
        ).clone().enqueue(object : Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                Toast.makeText(
                    requireActivity(),
                    response.body()?.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {

            }

        })


    }

    override fun onProgressUpdate(percentage: Int) {

    }


}

