package com.doubleclick.doctorapp.android.Home.DoctorConfig

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.doubleclick.doctorapp.android.Adapters.DoctorReservationAdapter
import com.doubleclick.doctorapp.android.Adapters.MyPatientOfDoctorAdapter
import com.doubleclick.doctorapp.android.Model.PatientReservations.PatientOldReservation.MyPatientReservation
import com.doubleclick.doctorapp.android.Model.PatientReservations.ShowPatientOfDoctor.ShowPatientOfDoctor
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.FragmentMyPatientsBinding
import com.doubleclick.doctorapp.android.utils.Constants
import com.doubleclick.doctorapp.android.utils.SessionManger.getIdWorker
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyPatientsFragment : Fragment() {

    private lateinit var binding: FragmentMyPatientsBinding
    private lateinit var viewModel: MainViewModel
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
        binding = FragmentMyPatientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            TOKEN = Constants.BEARER + requireActivity().getToken().toString()
            doctor_id = requireActivity().getIdWorker().toString()

            viewModel.getPatientVisitDoctorList(
                TOKEN,
                id = doctor_id
            )
                .observe(viewLifecycleOwner) {
                    it.clone().enqueue(object : Callback<MyPatientReservation> {
                        override fun onResponse(
                            call: Call<MyPatientReservation>,
                            response: Response<MyPatientReservation>
                        ) {
                            if (response.body()?.data != null) {
                                binding.rvMyPatients.adapter =
                                    MyPatientOfDoctorAdapter(response.body()?.data!!)
                            }
                        }

                        override fun onFailure(call: Call<MyPatientReservation>, t: Throwable) {

                        }

                    })
                }
        }

    }
}