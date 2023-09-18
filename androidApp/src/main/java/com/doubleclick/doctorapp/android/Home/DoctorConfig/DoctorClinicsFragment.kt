package com.doubleclick.doctorapp.android.Home.DoctorConfig

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doubleclick.doctorapp.android.Adapters.MyClinicDoctorAdapter
import com.doubleclick.doctorapp.android.Adapters.SpinnerAreaAdapter
import com.doubleclick.doctorapp.android.Adapters.SpinnerGovernoratesAdapter
import com.doubleclick.doctorapp.android.Model.*
import com.doubleclick.doctorapp.android.Model.Clinic.AddClinics
import com.doubleclick.doctorapp.android.Model.Area.Araes
import com.doubleclick.doctorapp.android.Model.Area.AreaModel
import com.doubleclick.doctorapp.android.Model.Clinic.ClinicList
import com.doubleclick.doctorapp.android.Model.Days.DaysAtClinic
import com.doubleclick.doctorapp.android.Model.Days.DaysAtClinicModel
import com.doubleclick.doctorapp.android.Model.Governorates.Governorates
import com.doubleclick.doctorapp.android.Model.Governorates.GovernoratesModel
import com.doubleclick.doctorapp.android.OnSpinnerEventsListener
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.FragmentDoctorClinicsBinding
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.SessionManger.getIdWorker
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import com.doubleclick.doctorapp.android.views.SeekArc.SeekArc
import com.doubleclick.doctorapp.android.views.SeekArc.`interface`.OnSeekArcChangeListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class DoctorClinicsFragment : Fragment() {

    private lateinit var binding: FragmentDoctorClinicsBinding
    private lateinit var viewModel: MainViewModel
    private val TAG = "DoctorClinicsFragment"
    private var TOKEN: String = ""
    private var doctor_id: String = ""

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
        binding = FragmentDoctorClinicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]

        binding.addClinic.setOnClickListener {
            findNavController().navigate(DoctorClinicsFragmentDirections.actionDoctorClinicsFragmentToAddClinicFragment())
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            TOKEN = BEARER + requireActivity().getToken().toString()
            doctor_id = requireActivity().getIdWorker().toString()

            viewModel.getClinicList(TOKEN).observe(viewLifecycleOwner) {
                it.clone().enqueue(object : Callback<ClinicList> {
                    override fun onResponse(
                        call: Call<ClinicList>,
                        response: Response<ClinicList>
                    ) {
                        if (response.body()?.data != null) {

                            binding.rvClinics.adapter =
                                MyClinicDoctorAdapter(
                                    response.body()?.data!!.filter { clinic ->
                                        clinic.doctor_id.toString() == doctor_id
                                    }.toMutableList()
                                )
                        }
                    }

                    override fun onFailure(call: Call<ClinicList>, t: Throwable) {

                    }
                })
            }
        }
    }

}