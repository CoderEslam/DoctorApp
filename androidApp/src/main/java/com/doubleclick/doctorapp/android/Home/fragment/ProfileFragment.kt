package com.doubleclick.doctorapp.android.Home.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.doubleclick.doctorapp.android.Adapters.AdapterFamilyMember
import com.doubleclick.doctorapp.android.Adapters.SpinnerAdapter
import com.doubleclick.doctorapp.android.Adapters.SpinnerGovernoratesAdapter
import com.doubleclick.doctorapp.android.Model.Area.Araes
import com.doubleclick.doctorapp.android.Model.Governorates.Governorates
import com.doubleclick.doctorapp.android.Model.Governorates.GovernoratesModel
import com.doubleclick.doctorapp.android.Model.Message
import com.doubleclick.doctorapp.android.Model.Patient.PatientStore
import com.doubleclick.doctorapp.android.Model.Patient.PatientsList
import com.doubleclick.doctorapp.android.OnSpinnerEventsListener
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.FragmentProfileBinding
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.Constants.IMAGE_URL_USERS
import com.doubleclick.doctorapp.android.utils.Constants.TOKEN
import com.doubleclick.doctorapp.android.utils.SessionManger
import com.doubleclick.doctorapp.android.utils.SessionManger.getCurrentPassword
import com.doubleclick.doctorapp.android.utils.SessionManger.getId
import com.doubleclick.doctorapp.android.utils.SessionManger.getImage
import com.doubleclick.doctorapp.android.utils.SessionManger.getName
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import com.doubleclick.doctorapp.android.utils.SessionManger.setImage
import com.doubleclick.doctorapp.android.utils.UploadRequestBody
import com.doubleclick.doctorapp.android.utils.expand
import com.doubleclick.doctorapp.android.utils.getFileName
import com.doubleclick.doctorapp.android.views.CustomSpinner.CustomSpinner
import com.iceteck.silicompressorr.SiliCompressor
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream


class ProfileFragment : Fragment(), UploadRequestBody.UploadCallback {

    private lateinit var binding: FragmentProfileBinding

    private val smokingList = listOf("No", "Yes")
    private val alcoholDrinkingList = listOf("No", "Yes")
    private val bloodTypeList = listOf("A+", "B+", "O+", "AB+", "A-", "B-", "O-", "AB-")
    private val materielStatusList = listOf("أعزب", "متزوج", "أرمل", "مطلق")
    private lateinit var uri: Uri;
    private val TAG = "ProfileFragment"
    private lateinit var viewModel: MainViewModel
    private var governoratesModelList: List<GovernoratesModel> = mutableListOf()
    private lateinit var governoratesModel: GovernoratesModel
    private var areaModelList: List<GovernoratesModel> = mutableListOf()
    private lateinit var areaModel: GovernoratesModel

    private var patient_id: String = ""
    private var TOKEN: String = ""

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
            TOKEN = BEARER + requireActivity().getToken().toString()
            Log.e("TOKEN", "onViewCreated: $TOKEN")
            patient_id = requireActivity().getId().toString()
            Glide.with(requireActivity()).load(
                "$IMAGE_URL_USERS${requireActivity().getName()}_${requireActivity().getId()}.jpg"
            ).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imageProfile)

            viewModel.familyMemberPatient(TOKEN)
                .observe(viewLifecycleOwner) {
                    it.clone().enqueue(object : Callback<PatientsList> {
                        override fun onResponse(
                            call: Call<PatientsList>,
                            response: Response<PatientsList>
                        ) {
                            Log.e(TAG, "onResponse: ${response.body()!!.data!!}")
                            binding.rvFamilyMember.adapter =
                                AdapterFamilyMember(response.body()!!.data!!.toMutableList())
                        }

                        override fun onFailure(call: Call<PatientsList>, t: Throwable) {
                            Log.e(TAG, "onFailure: ${t.message}")

                        }

                    })
                }

            binding.addFamilyMember.setOnClickListener {
                // Create the object of AlertDialog Builder class
                val builder = AlertDialog.Builder(requireActivity(), R.style.alertDialog)
                val view = LayoutInflater.from(requireActivity())
                    .inflate(R.layout.add_family_member, null, false)
                val et_name: TextView = view.findViewById(R.id.et_name)
                val et_phone: TextView = view.findViewById(R.id.et_phone)
                val et_height: TextView = view.findViewById(R.id.et_height)
                val et_weight: TextView = view.findViewById(R.id.et_weight)
                val ll_family: LinearLayout = view.findViewById(R.id.ll_family)
                val spinner_gov: CustomSpinner = view.findViewById(R.id.spinner_gov)
                val spinner_area: CustomSpinner = view.findViewById(R.id.spinner_area)
                val spinner_blood: CustomSpinner = view.findViewById(R.id.spinner_blood)
                val spinner_smoking: CustomSpinner = view.findViewById(R.id.spinner_smoking)
                val spinner_drink_alcohol: CustomSpinner =
                    view.findViewById(R.id.spinner_drink_alcohol)
                val spinner_marital_status: CustomSpinner =
                    view.findViewById(R.id.spinner_marital_status)
                val arrow_spinner_area: ImageView = view.findViewById(R.id.arrow_spinner_area)
                val arrow_spinner_gov: ImageView = view.findViewById(R.id.arrow_spinner_gov)
                val arrow_spinner_blood: ImageView = view.findViewById(R.id.arrow_spinner_blood)
                val arrow_spinner_smoking: ImageView = view.findViewById(R.id.arrow_spinner_smoking)
                val arrow_spinner_drink_alcohol: ImageView =
                    view.findViewById(R.id.arrow_spinner_drink_alcohol)
                val arrow_spinner_marital_status: ImageView =
                    view.findViewById(R.id.arrow_spinner_marital_status)
                setUpSpinner(spinner_gov, arrow_spinner_gov, requireActivity())
                setUpSpinner(spinner_area, arrow_spinner_area, requireActivity())
                val boold = setUpSpinner(
                    spinner_blood,
                    arrow_spinner_blood,
                    requireActivity(),
                    bloodTypeList
                )
                val smoking = setUpSpinner(
                    spinner_smoking,
                    arrow_spinner_smoking,
                    requireActivity(),
                    smokingList
                )
                val drink = setUpSpinner(
                    spinner_drink_alcohol,
                    arrow_spinner_drink_alcohol,
                    requireActivity(),
                    alcoholDrinkingList
                )
                val status = setUpSpinner(
                    spinner_marital_status,
                    arrow_spinner_marital_status,
                    requireActivity(),
                    materielStatusList
                )
                builder.setView(view)
                viewModel.getGovernoratesList(TOKEN)
                    .observe(viewLifecycleOwner) {
                        it.clone().enqueue(object : Callback<Governorates> {
                            override fun onResponse(
                                call: Call<Governorates>,
                                response: Response<Governorates>
                            ) {
                                governoratesModelList = response.body()!!.data
                                setUpSpinnerGov(
                                    spinner_gov,
                                    arrow_spinner_gov,
                                    governoratesModelList
                                )

                            }

                            override fun onFailure(call: Call<Governorates>, t: Throwable) {
                                Log.e(TAG, "onFailure: ${t.message}")
                            }

                        })
                    }
                viewModel.getAreaList(TOKEN)
                    .observe(viewLifecycleOwner) {
                        it.clone().enqueue(object : Callback<Araes> {
                            override fun onResponse(
                                call: Call<Araes>,
                                response: Response<Araes>
                            ) {
                                areaModelList = response.body()!!.data
                                setUpSpinnerArea(spinner_area, arrow_spinner_area, areaModelList)

                            }

                            override fun onFailure(call: Call<Araes>, t: Throwable) {
                                Log.e(TAG, "onFailure: ${t.message}")
                            }

                        })
                    }
                // Set Alert Title
                builder.setTitle("Add Family Member")

                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Yes") { dialog, which ->
                    addPatients(
                        PatientStore(
                            name = et_name.text.toString().trim(),
                            phone = et_phone.text.toString().trim(),
                            governorate_id = governoratesModel.id.toString(),
                            area_id = areaModel.id.toString(),
                            smoking = smoking,
                            alcohol_drinking = drink,
                            weight = et_weight.text.toString().trim(),
                            height = et_height.text.toString().trim(),
                            blood_type = boold,
                            materiel_status = status
                        )
                    )
                }

                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("No") { dialog, which ->
                    dialog.cancel()
                }
                view.expand(ll_family)
                builder.show()
            }


        }


    }

    private fun openImage() {
        getContent.launch("image/*")
    }


    private fun addPatients(patientStore: PatientStore) {
        viewModel.postfamilyMemberPatient(TOKEN, patientStore = patientStore)
            .observe(viewLifecycleOwner) {
                it.clone().enqueue(object : Callback<Message> {
                    override fun onResponse(
                        call: Call<Message>,
                        response: Response<Message>
                    ) {

                    }

                    override fun onFailure(call: Call<Message>, t: Throwable) {

                    }

                })
            }
    }

    private fun setUpSpinner(
        spinner: CustomSpinner,
        arrow: ImageView,
        context: Context,
        list: List<String>? = null
    ): String {
        var selected = ""
        if (list != null) {
            spinner.adapter = SpinnerAdapter(context, list)
        }
        spinner.setSpinnerEventsListener(object :
            OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                arrow.rotation = 180f

            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                arrow.rotation = 0f


            }

        })
        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                    selected = list?.get(i).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
        return selected;

    }


    private fun setUpSpinnerGov(
        spinner: CustomSpinner,
        arrow: ImageView,
        list: List<GovernoratesModel>
    ) {
        spinner.adapter = SpinnerGovernoratesAdapter(list)
        spinner.setSpinnerEventsListener(object :
            OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                arrow.rotation = 180f
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                arrow.rotation = 0f
            }

        })
        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                    areaModel = list[i]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
    }

    private fun setUpSpinnerArea(
        spinner: CustomSpinner,
        arrow: ImageView,
        list: List<GovernoratesModel>
    ) {
        spinner.adapter = SpinnerGovernoratesAdapter(list)
        spinner.setSpinnerEventsListener(object :
            OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                arrow.rotation = 180f

            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                arrow.rotation = 0f


            }

        })
        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                    governoratesModel = list[i]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun uploadImage(body: UploadRequestBody) {
        binding.progressBar.visibility = View.VISIBLE
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
                                "$IMAGE_URL_USERS${requireActivity().getImage()}"
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