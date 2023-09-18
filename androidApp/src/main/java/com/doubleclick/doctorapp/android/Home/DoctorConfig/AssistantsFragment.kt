package com.doubleclick.doctorapp.android.Home.DoctorConfig

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doubleclick.doctorapp.android.Adapters.AdapterDoctorAssistant
import com.doubleclick.doctorapp.android.Adapters.MyClinicDoctorAdapter
import com.doubleclick.doctorapp.android.Adapters.SpinnerClinicsAdapter
import com.doubleclick.doctorapp.android.DeleteAssistant
import com.doubleclick.doctorapp.android.Model.Assistants.AddAssistants
import com.doubleclick.doctorapp.android.Model.Assistants.AssistantsList
import com.doubleclick.doctorapp.android.Model.Assistants.AssistantsModel
import com.doubleclick.doctorapp.android.Model.Clinic.ClinicList
import com.doubleclick.doctorapp.android.Model.Clinic.ClinicModel
import com.doubleclick.doctorapp.android.Model.Message
import com.doubleclick.doctorapp.android.OnSpinnerEventsListener
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.api.RetrofitInstance
import com.doubleclick.doctorapp.android.databinding.FragmentAssistantsBinding
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.Constants.TOKEN
import com.doubleclick.doctorapp.android.utils.SessionManger.getIdWorker
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import kotlinx.android.synthetic.main.menu_left_drawer.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AssistantsFragment : Fragment(), DeleteAssistant {

    private lateinit var binding: FragmentAssistantsBinding;
    private lateinit var viewModel: MainViewModel
    private var assistantsModelList: MutableList<AssistantsModel> = mutableListOf()
    private var clinics: MutableList<ClinicModel> = mutableListOf()
    private lateinit var clinic: ClinicModel
    private lateinit var adapterDoctorAssistant: AdapterDoctorAssistant
    private val TAG = "AssistantsFragment"
    private var TOKEN = ""
    private var doctor_id = ""


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
        binding = FragmentAssistantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this, MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            TOKEN = BEARER + requireActivity().getToken()
            doctor_id = requireActivity().getIdWorker().toString()
            binding.addAssistant.setOnClickListener {
                viewModel.postAssistants(
                    TOKEN,
                    AddAssistants(
                        name = binding.assistantName.text.toString(),
                        email = binding.assistantEmail.text.toString(),
                        password = binding.assistantPassword.text.toString(),
                        password_confirmation = binding.assistantPassword.text.toString(),
                        clinic_id = clinic.id.toString(),
                        phone = binding.assistantPhone.text.toString(),
                        doctor_id = doctor_id
                    )
                ).observe(viewLifecycleOwner) {
                    it.clone().enqueue(object : Callback<Message> {
                        override fun onResponse(call: Call<Message>, response: Response<Message>) {
                            if (!response.body()?.message.isNullOrEmpty()) {
                                binding.assistantName.setText("")
                                binding.assistantPhone.setText("")
                                binding.assistantEmail.setText("")
                                binding.assistantPassword.setText("")
                                Toast.makeText(
                                    requireActivity(),
                                    "${response.body()?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                getAssistant()
                            }
                        }

                        override fun onFailure(call: Call<Message>, t: Throwable) {
                            Log.e(TAG, "onFailure: ${t.message}")
                        }

                    })
                }
            }

            viewModel.getClinicList(TOKEN).observe(viewLifecycleOwner) {
                it.clone().enqueue(object : Callback<ClinicList> {
                    override fun onResponse(
                        call: Call<ClinicList>,
                        response: Response<ClinicList>
                    ) {
                        if (response.body()?.data != null) {
                            clinics = response.body()?.data!!
                                .filter { clinic ->
                                    clinic.doctor_id.toString() == doctor_id
                                }
                                .toMutableList()
                            binding.spinnerAssistantClinic.adapter =
                                SpinnerClinicsAdapter(clinics)
                            setUpSpinner()
                        }
                    }

                    override fun onFailure(call: Call<ClinicList>, t: Throwable) {

                    }
                })
            }

        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        getAssistant()
    }

    private fun setUpSpinner() {
        binding.spinnerAssistantClinic.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                binding.arrowClinic.rotation = 180f
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                binding.arrowClinic.rotation = 0f
            }

        })
        binding.spinnerAssistantClinic.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                    try {
                        clinic = clinics[i]
                    } catch (e: IndexOutOfBoundsException) {
                        e.printStackTrace()
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    try {
                        clinic = clinics[0]
                    } catch (e: IndexOutOfBoundsException) {
                        e.printStackTrace()
                    }
                }

            }
    }

    private fun getAssistant() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getAssistants("$BEARER${requireActivity().getToken()}")
                .observe(viewLifecycleOwner) {
                    it.clone().enqueue(object : Callback<AssistantsList> {
                        override fun onResponse(
                            call: Call<AssistantsList>,
                            response: Response<AssistantsList>
                        ) {
                            if (response.body()?.data != null) {
                                assistantsModelList = response.body()?.data!!.toMutableList()
                                adapterDoctorAssistant = AdapterDoctorAssistant(
                                    assistantsModelList,
                                    this@AssistantsFragment
                                )
                                binding.rvDoctorAssistant.adapter = adapterDoctorAssistant
                            }

                        }

                        override fun onFailure(call: Call<AssistantsList>, t: Throwable) {

                        }

                    })
                }
        }
    }

    override fun delete(assistantsModel: AssistantsModel) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            RetrofitInstance.api.deleteAssistants(
                "$BEARER${requireActivity().getToken()}",
                id = assistantsModel.id.toString()
            )
                .clone()
                .enqueue(object : Callback<Message> {
                    override fun onResponse(call: Call<Message>, response: Response<Message>) {
                        adapterDoctorAssistant.notifyItemRemoved(
                            assistantsModelList.indexOf(
                                assistantsModel
                            )
                        )
                        assistantsModelList.remove(assistantsModel)

                        Toast.makeText(requireActivity(), "Deleted", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<Message>, t: Throwable) {

                    }

                })
        }
    }

}