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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.doubleclick.doctorapp.android.Adapters.AdapterFamilyMember
import com.doubleclick.doctorapp.android.Adapters.SpinnerAdapter
import com.doubleclick.doctorapp.android.Adapters.SpinnerAreaAdapter
import com.doubleclick.doctorapp.android.Adapters.SpinnerGovernoratesAdapter
import com.doubleclick.doctorapp.android.FamilyOption
import com.doubleclick.doctorapp.android.Model.Area.Araes
import com.doubleclick.doctorapp.android.Model.Area.AreaModel
import com.doubleclick.doctorapp.android.Model.Auth.UpdateAccount
import com.doubleclick.doctorapp.android.Model.Auth.UpdateUser
import com.doubleclick.doctorapp.android.Model.Governorates.Governorates
import com.doubleclick.doctorapp.android.Model.Governorates.GovernoratesModel
import com.doubleclick.doctorapp.android.Model.Message
import com.doubleclick.doctorapp.android.Model.Patient.PatientStore
import com.doubleclick.doctorapp.android.Model.Patient.PatientsList
import com.doubleclick.doctorapp.android.Model.PatientReservations.PatientReservationsList
import com.doubleclick.doctorapp.android.OnSpinnerEventsListener
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.api.RetrofitInstance
import com.doubleclick.doctorapp.android.databinding.FragmentProfileUserBinding
import com.doubleclick.doctorapp.android.utils.*
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.Constants.IMAGE_URL_USERS
import com.doubleclick.doctorapp.android.utils.SessionManger.getCurrentUserEmail
import com.doubleclick.doctorapp.android.utils.SessionManger.getId
import com.doubleclick.doctorapp.android.utils.SessionManger.getImage
import com.doubleclick.doctorapp.android.utils.SessionManger.getName
import com.doubleclick.doctorapp.android.utils.SessionManger.getPhone
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import com.doubleclick.doctorapp.android.utils.SessionManger.setEmail
import com.doubleclick.doctorapp.android.utils.SessionManger.setImage
import com.doubleclick.doctorapp.android.utils.SessionManger.setName
import com.doubleclick.doctorapp.android.utils.SessionManger.setPhone
import com.doubleclick.doctorapp.android.utils.SessionManger.setToken
import com.doubleclick.doctorapp.android.views.CustomSpinner.CustomSpinner
import com.google.android.material.snackbar.Snackbar
import com.iceteck.silicompressorr.SiliCompressor
import kotlinx.android.synthetic.main.layout_family_member.*
import kotlinx.android.synthetic.main.menu_left_drawer.*
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


class ProfileUserFragment : Fragment(), UploadRequestBody.UploadCallback, FamilyOption {

    private lateinit var binding: FragmentProfileUserBinding

    private lateinit var smokingList: List<String>
    private lateinit var alcoholDrinkingList: List<String>
    private lateinit var bloodTypeList: List<String>
    private lateinit var materielStatusList: List<String>
    private var materielStatusSelected: Int = -1
    private var smokingSelected: Int = -1
    private var alcoholDrinkingSelected: Int = -1
    private var bloodSelected: String = ""
    private lateinit var uri: Uri;
    private val TAG = "ProfileFragment"
    private lateinit var viewModel: MainViewModel
    private var governoratesModelList: List<GovernoratesModel> = mutableListOf()
    private lateinit var governoratesModel: GovernoratesModel
    private var areaModelList: List<AreaModel> = mutableListOf()
    private lateinit var areaModel: AreaModel

    private var patient_id: String = ""
    private var TOKEN: String = ""

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            try {
                this.uri = uri!!
                val filePath = SiliCompressor.with(requireActivity()).compress(
                    uri.toString(), File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .toString() + "/Doctor/Images/"
                    )
                )
                binding.imageProfile.setImageURI(Uri.parse(filePath))
                val parcelFileDescriptor = requireActivity().contentResolver.openFileDescriptor(
                    Uri.parse(filePath)!!, "r", null
                ) ?: return@registerForActivityResult
                val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                val file = File(
                    requireActivity().cacheDir,
                    requireActivity().contentResolver.getFileName(Uri.parse(filePath)!!)
                )
                binding.progressBar.visibility = View.VISIBLE
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                val body = UploadRequestBody(file, "image", this@ProfileUserFragment)
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileUserBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smokingList = resources.getStringArray(R.array.smokingList).toList()
        alcoholDrinkingList = resources.getStringArray(R.array.alcoholDrinkingList).toList()
        bloodTypeList = resources.getStringArray(R.array.bloodTypeList).toList()
        materielStatusList = resources.getStringArray(R.array.materielStatusList).toList()

        viewModel = ViewModelProvider(
            this, MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]

        binding.chooseImage.setOnClickListener {
            openImage()
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            binding.etName.setText(requireActivity().getName().toString())
            binding.etEmail.setText(requireActivity().getCurrentUserEmail().toString())
            binding.etNumberContact.setText(requireActivity().getPhone().toString())
            TOKEN = BEARER + requireActivity().getToken().toString()
            patient_id = requireActivity().getId().toString()
            Glide.with(requireActivity()).load(
                "$IMAGE_URL_USERS${requireActivity().getName()}_${requireActivity().getId()}.jpg"
            ).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .into(binding.imageProfile)

            familyMember()

            binding.addFamilyMember.setOnClickListener {
                // Create the object of AlertDialog Builder class
                val builder = AlertDialog.Builder(requireActivity(), R.style.alertDialog)
                val view = LayoutInflater.from(requireActivity())
                    .inflate(R.layout.add_family_member, null, false)
                val et_name: TextView = view.findViewById(R.id.et_name)
                val et_phone: TextView = view.findViewById(R.id.et_phone)
                val et_height: TextView = view.findViewById(R.id.et_height)
                val et_weight: TextView = view.findViewById(R.id.et_weight)
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

                setUpSpinnerBlood(spinner_blood, arrow_spinner_blood, requireActivity())
                setUpSpinnerSmoking(spinner_smoking, arrow_spinner_smoking, requireActivity())
                setUpSpinnerDrinkAlcohol(
                    spinner_drink_alcohol, arrow_spinner_drink_alcohol, requireActivity()
                )
                setUpSpinnerStatus(
                    spinner_marital_status, arrow_spinner_marital_status, requireActivity()
                )

                builder.setView(view)
                viewModel.getGovernoratesList(TOKEN).observe(viewLifecycleOwner) {
                    it.clone().enqueue(object : Callback<Governorates> {
                        override fun onResponse(
                            call: Call<Governorates>, response: Response<Governorates>
                        ) {
                            governoratesModelList = response.body()!!.data
                            setUpSpinnerGov(
                                spinner_gov, arrow_spinner_gov, governoratesModelList
                            )
                        }

                        override fun onFailure(call: Call<Governorates>, t: Throwable) {
                            Log.e(TAG, "onFailure: ${t.message}")
                        }

                    })
                }
                viewModel.getAreaList(TOKEN).observe(viewLifecycleOwner) {
                    it.clone().enqueue(object : Callback<Araes> {
                        override fun onResponse(
                            call: Call<Araes>, response: Response<Araes>
                        ) {
                            areaModelList = response.body()!!.data!!
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
                            smoking = smokingSelected.toString(),
                            alcohol_drinking = alcoholDrinkingSelected.toString(),
                            weight = et_weight.text.toString().trim(),
                            height = et_height.text.toString().trim(),
                            blood_type = bloodSelected,
                            materiel_status = materielStatusSelected.toString()
                        )
                    )
                }

                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("No") { dialog, which ->
                    dialog.cancel()
                }
                builder.show()
            }

            binding.save.setOnClickListener {
                if (validation()) {
                    viewModel.updateAccount(
                        token = TOKEN, updateAccount = UpdateAccount(
                            name = binding.etName.text.toString(),
                            phone = binding.etNumberContact.text.toString(),
                            email = binding.etEmail.text.toString()
                        )
                    ).observe(viewLifecycleOwner) {
                        it.clone().enqueue(object : Callback<UpdateUser> {
                            override fun onResponse(
                                call: Call<UpdateUser>, response: Response<UpdateUser>
                            ) {
                                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                                    response.body()?.user?.name?.let { name ->
                                        requireActivity().setName(
                                            name
                                        )
                                    }
                                    response.body()?.user?.device_token?.let { device_token ->
                                        requireActivity().setToken(
                                            device_token
                                        )
                                    }
                                    response.body()?.user?.phone?.let { phone ->
                                        requireActivity().setPhone(
                                            phone
                                        )
                                    }
                                    response.body()?.user?.email?.let { email ->
                                        requireActivity().setEmail(
                                            email
                                        )
                                    }
                                }

                                Toast.makeText(
                                    requireActivity(),
                                    response.body()?.message.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            override fun onFailure(call: Call<UpdateUser>, t: Throwable) {

                            }

                        })
                    }
                } else {
                    Toast.makeText(requireActivity(), "Something wrong", Toast.LENGTH_LONG).show()
                }
            }


        }


    }

    private fun familyMember() {
        viewModel.getFamilyMemberPatient(TOKEN).observe(viewLifecycleOwner) {
            it.clone().enqueue(object : Callback<PatientsList> {
                override fun onResponse(
                    call: Call<PatientsList>,
                    response: Response<PatientsList>
                ) {
                    try {
                        if (response.body()?.data != null) {
                            binding.rvFamilyMember.adapter = AdapterFamilyMember(
                                this@ProfileUserFragment,
                                response.body()!!.data!!.toMutableList()
                            )
                        }
                    } catch (e: NullPointerException) {
                        Log.e(TAG, "onResponse: ${e.message}")
                    }
                }

                override fun onFailure(call: Call<PatientsList>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        }
    }

    private fun validation(): Boolean {
        return binding.etEmail.isNotNullOrEmptyEditText() &&
                binding.etName.isNotNullOrEmptyEditText() &&
                binding.etNumberContact.isNotNullOrEmptyEditText()
    }

    private fun openImage() {
        getContent.launch("image/*")
    }


    private fun addPatients(patientStore: PatientStore) {
        //PatientStore(name=fb, status=Active, phone=dfbd, telephone=, notes=, governorate_id=2, area_id=6, smoking=0, weight=585, alcohol_drinking=0, height=22, blood_type=A+, materiel_status=0)
        Log.e(TAG, "addPatients: ${patientStore.toString()}")
        viewModel.postFamilyMemberPatient(
            TOKEN, patientStore = patientStore
        ).observe(viewLifecycleOwner) {
            it.clone().enqueue(object : Callback<Message> {
                override fun onResponse(
                    call: Call<Message>, response: Response<Message>
                ) {
                    familyMember()
                    Snackbar.make(requireView(), "Done", Snackbar.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Message>, t: Throwable) {

                }

            })
        }
    }

    private fun setUpSpinnerDrinkAlcohol(
        spinner: CustomSpinner, arrow: ImageView, context: Context
    ) {
        spinner.adapter = SpinnerAdapter(context, alcoholDrinkingList)

        spinner.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                arrow.rotation = 180f
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                arrow.rotation = 0f
            }

        })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                alcoholDrinkingSelected = i
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                alcoholDrinkingSelected = 0
            }

        }
    }

    private fun setUpSpinnerSmoking(spinner: CustomSpinner, arrow: ImageView, context: Context) {
        spinner.adapter = SpinnerAdapter(context, smokingList)

        spinner.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                arrow.rotation = 180f
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                arrow.rotation = 0f
            }

        })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                smokingSelected = i
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                smokingSelected = 0
            }

        }
    }

    private fun setUpSpinnerStatus(spinner: CustomSpinner, arrow: ImageView, context: Context) {
        spinner.adapter = SpinnerAdapter(context, materielStatusList)

        spinner.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                arrow.rotation = 180f
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                arrow.rotation = 0f
            }

        })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                materielStatusSelected = i
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                materielStatusSelected = 0
            }

        }

    }

    private fun setUpSpinnerBlood(spinner: CustomSpinner, arrow: ImageView, context: Context) {
        spinner.adapter = SpinnerAdapter(context, bloodTypeList)
        spinner.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                arrow.rotation = 180f
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                arrow.rotation = 0f
            }

        })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                bloodSelected = bloodTypeList[i]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                bloodSelected = bloodTypeList[0]
            }

        }
    }

    private fun setUpSpinnerGov(
        spinner: CustomSpinner, arrow: ImageView, list: List<GovernoratesModel>
    ) {
        spinner.adapter = SpinnerGovernoratesAdapter(list)
        spinner.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                arrow.rotation = 180f
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                arrow.rotation = 0f
            }

        })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                governoratesModel = list[i]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                governoratesModel = list[0]
            }

        }
    }

    private fun setUpSpinnerArea(spinner: CustomSpinner, arrow: ImageView, list: List<AreaModel>) {
        spinner.adapter = SpinnerAreaAdapter(list)
        spinner.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                arrow.rotation = 180f
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                arrow.rotation = 0f
            }

        })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                areaModel = list[i]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                areaModel = list[0]
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
                        call: Call<Message>, response: Response<Message>
                    ) {
                        binding.progressBar.visibility = View.GONE
                        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                            requireActivity().setImage("${name}_$id.jpg")
                            Glide.with(requireActivity()).load(
                                "$IMAGE_URL_USERS${requireActivity().getImage()}"
                            ).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                .into(binding.imageProfile)
                        }
                    }

                    override fun onFailure(
                        call: Call<Message>, t: Throwable
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

    override fun patientReservations(patient_reservations_id: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            RetrofitInstance.api.getPatientReservations(
                BEARER + requireActivity().getToken(), patient_reservations_id
            ).clone().enqueue(object : Callback<PatientReservationsList> {
                override fun onResponse(
                    call: Call<PatientReservationsList>, response: Response<PatientReservationsList>
                ) {
                    if (response.body()?.data != null) {
                        BottomDialogPatiant(response.body()?.data!!).show(childFragmentManager, "")
                    }
                }

                override fun onFailure(call: Call<PatientReservationsList>, t: Throwable) {

                }

            })
        }
    }

    override fun patientVisits(patient_visits_id: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            RetrofitInstance.api.getPatientReservations(
                BEARER + requireActivity().getToken(), patient_visits_id
            ).clone().enqueue(object : Callback<PatientReservationsList> {
                override fun onResponse(
                    call: Call<PatientReservationsList>, response: Response<PatientReservationsList>
                ) {
                    if (response.body()?.data != null) {
                        BottomDialogPatiant(response.body()?.data!!).show(childFragmentManager, "")
                    }
                }

                override fun onFailure(call: Call<PatientReservationsList>, t: Throwable) {

                }

            })
        }
    }


}