package com.doubleclick.doctorapp.android.Home.fragment

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.doubleclick.doctorapp.android.Model.Message
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.FragmentProfileBinding
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.Constants.IMAGE_URL
import com.doubleclick.doctorapp.android.utils.SessionManger
import com.doubleclick.doctorapp.android.utils.SessionManger.getCurrentPassword
import com.doubleclick.doctorapp.android.utils.SessionManger.getId
import com.doubleclick.doctorapp.android.utils.SessionManger.getImage
import com.doubleclick.doctorapp.android.utils.SessionManger.getName
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import com.doubleclick.doctorapp.android.utils.SessionManger.setImage
import com.doubleclick.doctorapp.android.utils.UploadRequestBody
import com.doubleclick.doctorapp.android.utils.getFileName
import com.facebook.react.packagerconnection.ReconnectingWebSocket
import com.iceteck.silicompressorr.SiliCompressor
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.apache.http.client.utils.CloneUtils.clone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream


class ProfileFragment : Fragment(), UploadRequestBody.UploadCallback {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var uri: Uri;
    private val TAG = "ProfileFragment"
    private lateinit var viewModel: MainViewModel

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            try {
                this.uri = uri!!
                val filePath = SiliCompressor.with(requireActivity()).compress(
                    uri.toString(),
                    File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .toString() + "/Doctor/Images/"
                    )
                )
                binding.imageProfile.setImageURI(Uri.parse(filePath))
                val parcelFileDescriptor =
                    requireActivity().contentResolver.openFileDescriptor(
                        Uri.parse(filePath)!!,
                        "r",
                        null
                    )
                        ?: return@registerForActivityResult
                val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                val file =
                    File(
                        requireActivity().cacheDir,
                        requireActivity().contentResolver.getFileName(Uri.parse(filePath)!!)
                    )
                binding.progressBar.visibility = View.VISIBLE
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                val body = UploadRequestBody(file, "image", this@ProfileFragment)
                uploadImage(body)

            } catch (e: NullPointerException) {
                Log.e("registerForActivity", "registerForActivityResult: ${e.message}")
            } catch (e: FileNotFoundException) {
                Log.e("registerForActivity", "registerForActivityResult: ${e.message}")
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]

        binding.chooseImage.setOnClickListener {
            openImage()
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            binding.etName.setText(requireActivity().getName().toString())

            Glide.with(requireActivity()).load(
                "$IMAGE_URL${requireActivity().getName()}_${requireActivity().getId()}.jpg"
            ).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imageProfile)
        }


    }

    private fun openImage() {
        getContent.launch("image/*")
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun uploadImage(body: UploadRequestBody) {
        GlobalScope.launch(Dispatchers.Main) {
            val id = requireActivity().getId().toString()
            val name = requireActivity().getName().toString()
            viewModel.postUserImage(
                "$BEARER" + requireActivity().getToken(),
                MultipartBody.Part.createFormData("image", "${name}_$id.jpg", body)
            ).observe(viewLifecycleOwner) {
                it.clone().enqueue(object : Callback<Message> {
                    override fun onResponse(
                        call: Call<Message>,
                        response: Response<Message>
                    ) {
                        binding.progressBar.visibility = View.GONE
                        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                            requireActivity().setImage("${name}_$id.jpg")
                            Glide.with(requireActivity()).load(
                                "$IMAGE_URL${requireActivity().getImage()}"
                            ).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(binding.imageProfile)
                        }
                    }

                    override fun onFailure(
                        call: Call<Message>,
                        t: Throwable
                    ) {
                        Log.d("MultipartBody", "onFailure: ${t.message}")
                    }

                })
            }
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        binding.progressBar.progress = percentage
    }


}