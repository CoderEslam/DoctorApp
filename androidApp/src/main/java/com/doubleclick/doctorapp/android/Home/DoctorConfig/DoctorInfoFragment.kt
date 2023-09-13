package com.doubleclick.doctorapp.android.Home.DoctorConfig

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.doubleclick.doctorapp.android.Adapters.SpinnerGeneralSpecializationAdapter
import com.doubleclick.doctorapp.android.Adapters.SpinnerSpecializationAdapter
import com.doubleclick.doctorapp.android.Model.Doctor.DoctorsList
import com.doubleclick.doctorapp.android.Model.Doctor.UpdateDoctor
import com.doubleclick.doctorapp.android.Model.GeneralSpecialization.GeneralSpecializationList
import com.doubleclick.doctorapp.android.Model.GeneralSpecialization.GeneralSpecializationModel
import com.doubleclick.doctorapp.android.Model.Message
import com.doubleclick.doctorapp.android.Model.Specialization.SpecializationList
import com.doubleclick.doctorapp.android.Model.Specialization.SpecializationModel
import com.doubleclick.doctorapp.android.OnSpinnerEventsListener
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.FragmentDoctorInfoBinding
import com.doubleclick.doctorapp.android.utils.Constants
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.Constants.IMAGE_URL_DOCTORS
import com.doubleclick.doctorapp.android.utils.SessionManger.getId
import com.doubleclick.doctorapp.android.utils.SessionManger.getIdWorker
import com.doubleclick.doctorapp.android.utils.SessionManger.getImage
import com.doubleclick.doctorapp.android.utils.SessionManger.getName
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import com.doubleclick.doctorapp.android.utils.SessionManger.setImage
import com.doubleclick.doctorapp.android.utils.UploadRequestBody
import com.doubleclick.doctorapp.android.utils.getFileName
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


class DoctorInfoFragment : Fragment(), UploadRequestBody.UploadCallback {

    private lateinit var binding: FragmentDoctorInfoBinding
    private lateinit var viewModel: MainViewModel
    private val TAG = "DoctorSpecializationFra"
    private lateinit var uri: Uri;
    private var TOKEN: String = ""
    private var specializations: Int = -1
    private lateinit var specializationList: List<SpecializationModel>
    private lateinit var generalSpecializationList: List<GeneralSpecializationModel>
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
                binding.doctorImage.setImageURI(Uri.parse(filePath))
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
                val body = UploadRequestBody(file, "image", this@DoctorInfoFragment)
                uploadImage(body)

            } catch (e: NullPointerException) {
                Log.e("registerForActivity", "registerForActivityResult: ${e.message}")
            } catch (e: FileNotFoundException) {
                Log.e("registerForActivity", "registerForActivityResult: ${e.message}")
            }
        }

    private var general_specializations: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDoctorInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this, MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            binding.animationView.visibility = View.VISIBLE
            viewModel.getSpecializations("$BEARER${requireActivity().getToken()}")
                .observe(viewLifecycleOwner) {
                    it.enqueue(object : Callback<SpecializationList> {
                        override fun onResponse(
                            call: Call<SpecializationList>, response: Response<SpecializationList>
                        ) {
                            if (response.body()?.data != null) {
                                specializationList = response.body()?.data!!
                                binding.spinnerSpecializations.adapter =
                                    SpinnerSpecializationAdapter(specializationList)

                            }
                        }

                        override fun onFailure(call: Call<SpecializationList>, t: Throwable) {

                        }

                    })
                }

            viewModel.getGeneralSpecialties("$BEARER${requireActivity().getToken()}")
                .observe(viewLifecycleOwner) {
                    it.enqueue(object : Callback<GeneralSpecializationList> {
                        override fun onResponse(
                            call: Call<GeneralSpecializationList>,
                            response: Response<GeneralSpecializationList>
                        ) {
                            if (response.body()?.data != null) {
                                generalSpecializationList = response.body()?.data!!
                                binding.spinnerGeneralSpecializations.adapter =
                                    SpinnerGeneralSpecializationAdapter(
                                        generalSpecializationList
                                    )
                            }
                        }

                        override fun onFailure(
                            call: Call<GeneralSpecializationList>,
                            t: Throwable
                        ) {

                        }

                    })
                }

            viewModel.getDoctorsInfoById(
                "$BEARER${requireActivity().getToken()}",
                requireActivity().getIdWorker().toString() /*here must put id of doctor*/
            ).observe(viewLifecycleOwner) {
                it.enqueue(object : Callback<DoctorsList> {
                    override fun onResponse(
                        call: Call<DoctorsList>, response: Response<DoctorsList>
                    ) {
                        if (response.body()?.data != null) {
                            val data = response.body()?.data?.get(0)
                            binding.clinicName.setText(data?.name)
                            binding.websiteLink.setText(data?.website)
                            binding.facebookPageLink.setText(data?.facebook_page_link)
                            binding.facebookPageName.setText(data?.facebook_page_name)
                            binding.instagramPageLink.setText(data?.instagram_page_link)
                            binding.instagramPageName.setText(data?.instagram_page_name)
                            binding.animationView.visibility = View.GONE
                            Glide.with(this@DoctorInfoFragment)
                                .load(IMAGE_URL_DOCTORS + data?.doctor_image)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(binding.doctorImage)

                            /*binding.spinnerSpecializations.setSelection(
                                specializationList.data?.indexOf(
                                    data?.specialization
                                ), true
                            )*/
                            val general_specialty = data?.general_specialty_id?.let { it1 ->
                                SpecializationModel(
                                    id = it1,
                                    "",
                                    "",
                                    "",
                                    null,
                                    0
                                )
                            }
                            /* binding.spinnerGeneralSpecializations.setSelection(
                                 general_specializationList.data?.indexOf(
                                     general_specialty
                                 ), true
                             )*/
                        }
                    }

                    override fun onFailure(call: Call<DoctorsList>, t: Throwable) {

                    }

                })
            }

        }

        binding.saveChange.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                if (isFieldsEmpty() && requireActivity().getToken()
                        ?.isNotEmpty() == true
                ) viewModel.updateDoctor(
                    "$BEARER${requireActivity().getToken()}",
                    id = requireActivity().getIdWorker().toString(),
                    UpdateDoctor(
                        facebook_page_link = binding.facebookPageLink.text.toString(),
                        facebook_page_name = binding.facebookPageName.text.toString(),
                        general_specialty_id = general_specializations,
                        instagram_page_link = binding.instagramPageLink.text.toString(),
                        instagram_page_name = binding.instagramPageName.text.toString(),
                        name = binding.clinicName.text.toString(),
                        specialization_id = specializations,
                        status = "Active",
                        website = binding.websiteLink.text.toString()
                    )
                ).observe(viewLifecycleOwner) {
                    it.clone().enqueue(object : Callback<Message> {
                        override fun onResponse(call: Call<Message>, response: Response<Message>) {
                            if (response.body()?.message != null) {
                                Toast.makeText(
                                    requireActivity(),
                                    response.body()?.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<Message>, t: Throwable) {
                            Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }
        }

        binding.changeImage.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.spinnerSpecializations.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                    try {
                        specializations = specializationList?.get(i)?.id!!
                    } catch (_: IndexOutOfBoundsException) {
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    try {
                        specializations = specializationList?.get(0)?.id!!
                    } catch (_: IndexOutOfBoundsException) {
                    }
                }

            }

        binding.spinnerGeneralSpecializations.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                    try {
                        general_specializations = generalSpecializationList?.get(i)?.id!!
                    } catch (_: IndexOutOfBoundsException) {
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    try {
                        general_specializations = generalSpecializationList?.get(0)?.id!!
                    } catch (_: IndexOutOfBoundsException) {
                    }

                }

            }


        binding.spinnerGeneralSpecializations.setSpinnerEventsListener(object :
            OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                binding.spinnerGeneralSpecializations.background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.bg_spinner_down
                )
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                binding.spinnerGeneralSpecializations.background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.bg_spinner_up
                )
            }

        })

        binding.spinnerSpecializations.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                binding.spinnerSpecializations.background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.bg_spinner_down
                )
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                binding.spinnerSpecializations.background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.bg_spinner_up
                )
            }

        })
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun uploadImage(body: UploadRequestBody) {
        binding.progressBar.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.updateDoctorImage(
                BEARER + requireActivity().getToken(),
                requireActivity().getIdWorker().toString(),
                MultipartBody.Part.createFormData(
                    "image", "${System.currentTimeMillis()}.jpg", body
                )
            ).observe(viewLifecycleOwner) {
                it.clone().enqueue(object : Callback<Message> {
                    override fun onResponse(
                        call: Call<Message>,
                        response: Response<Message>
                    ) {
                        binding.progressBar.visibility = View.GONE
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

    private fun isFieldsEmpty(): Boolean = binding.clinicName.text.isNotEmpty()

    override fun onProgressUpdate(percentage: Int) {
        binding.progressBar.progress = percentage
    }


}