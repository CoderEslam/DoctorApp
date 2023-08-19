package com.doubleclick.doctorapp.android.Home.DoctorConfig

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doubleclick.doctorapp.android.Adapters.SpinnerSpecializationAdapter
import com.doubleclick.doctorapp.android.Model.Doctor.Doctor
import com.doubleclick.doctorapp.android.Model.Doctor.DoctorsList
import com.doubleclick.doctorapp.android.Model.Message
import com.doubleclick.doctorapp.android.Model.Specialization.Specialization
import com.doubleclick.doctorapp.android.OnSpinnerEventsListener
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.FragmentDoctorInfoBinding
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DoctorInfoFragment : Fragment() {

    private lateinit var binding: FragmentDoctorInfoBinding
    private lateinit var viewModel: MainViewModel
    private val TAG = "DoctorSpecializationFra"

    private var specializations: Int = -1
    private lateinit var specializationList: Specialization
    private lateinit var general_specializationList: Specialization


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
                    it.enqueue(object : Callback<Specialization> {
                        override fun onResponse(
                            call: Call<Specialization>, response: Response<Specialization>
                        ) {
                            specializationList = response.body()!!
                            binding.spinnerSpecializations.adapter =
                                SpinnerSpecializationAdapter(requireActivity(), specializationList)

                        }

                        override fun onFailure(call: Call<Specialization>, t: Throwable) {

                        }

                    })
                }

            viewModel.getGeneralSpecialties("$BEARER${requireActivity().getToken()}")
                .observe(viewLifecycleOwner) {
                    it.enqueue(object : Callback<Specialization> {
                        override fun onResponse(
                            call: Call<Specialization>, response: Response<Specialization>
                        ) {
                            general_specializationList = response.body()!!
                            binding.spinnerGeneralSpecializations.adapter =
                                SpinnerSpecializationAdapter(
                                    requireActivity(), general_specializationList
                                )

                        }

                        override fun onFailure(call: Call<Specialization>, t: Throwable) {

                        }

                    })
                }

            viewModel.getDoctorsInfoById("$BEARER${requireActivity().getToken()}", "3")
                .observe(viewLifecycleOwner) {
                    it.enqueue(object : Callback<DoctorsList> {
                        override fun onResponse(
                            call: Call<DoctorsList>,
                            response: Response<DoctorsList>
                        ) {
                            val data = response.body()!!.data[0]
                            binding.clinicName.setText(data.name)
                            binding.websiteLink.setText(data.website)
                            binding.facebookPageLink.setText(data.facebook_page_link)
                            binding.facebookPageName.setText(data.facebook_page_name)
                            binding.instagramPageLink.setText(data.instagram_page_link)
                            binding.instagramPageName.setText(data.instagram_page_name)
                            binding.animationView.visibility = View.GONE
                        }

                        override fun onFailure(call: Call<DoctorsList>, t: Throwable) {

                        }

                    })
                }

        }

        binding.doneSpecializations.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                if (isFieldsEmpty() && requireActivity().getToken()
                        ?.isNotEmpty() == true
                ) viewModel.postDoctor(
                    "$BEARER${requireActivity().getToken()}", Doctor(
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
                            Toast.makeText(
                                requireActivity(),
                                response.body()?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onFailure(call: Call<Message>, t: Throwable) {
                            Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }
        }

        binding.spinnerSpecializations.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                    try {
                        specializations = specializationList.data[i].id
                    } catch (_: IndexOutOfBoundsException) {
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    try {
                        specializations = specializationList.data[0].id
                    } catch (_: IndexOutOfBoundsException) {
                    }
                }

            }

        binding.spinnerGeneralSpecializations.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                    try {
                        general_specializations = general_specializationList.data[i].id
                    } catch (_: IndexOutOfBoundsException) {
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    try {
                        general_specializations = general_specializationList.data[0].id
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


    private fun isFieldsEmpty(): Boolean = binding.clinicName.text.isNotEmpty()


}