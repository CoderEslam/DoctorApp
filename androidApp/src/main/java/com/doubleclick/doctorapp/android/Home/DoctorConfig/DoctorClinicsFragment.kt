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
import com.doubleclick.doctorapp.android.Adapters.SpinnerAreaAdapter
import com.doubleclick.doctorapp.android.Adapters.SpinnerGovernoratesAdapter
import com.doubleclick.doctorapp.android.Model.*
import com.doubleclick.doctorapp.android.Model.Clinic.AddClinics
import com.doubleclick.doctorapp.android.Model.Area.Araes
import com.doubleclick.doctorapp.android.Model.Area.AreaModel
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
    private var governoratesModel: String = ""
    private var areaModel: String = ""
    private var governoratesModelList: List<GovernoratesModel> = mutableListOf()
    private var areaModelList: List<AreaModel> = mutableListOf()
    private lateinit var mTimePicker: TimePickerDialog
    private var daysList: MutableList<Int> = mutableListOf();
    private var daysAtClinicModel: List<DaysAtClinicModel> = mutableListOf()

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

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            TOKEN = requireActivity().getToken().toString()
            doctor_id = requireActivity().getIdWorker().toString()
            viewModel.getDoctorDaysAtClinicList("$BEARER$TOKEN")
                .observe(viewLifecycleOwner) {
                    it.enqueue(object : Callback<DaysAtClinic> {
                        override fun onResponse(
                            call: Call<DaysAtClinic>,
                            response: Response<DaysAtClinic>
                        ) {
                            daysAtClinicModel = response.body()!!.data
                            for (days in daysAtClinicModel) {
                                if (days.day_id == 0) {
                                    binding.saturday.isChecked = true
                                    daysList.add(0)
                                }
                                if (days.day_id == 1) {
                                    binding.sunday.isChecked = true
                                    daysList.add(1)
                                }
                                if (days.day_id == 2) {
                                    binding.monday.isChecked = true
                                    daysList.add(2)
                                }
                                if (days.day_id == 3) {
                                    binding.tuesday.isChecked = true
                                    daysList.add(3)
                                }
                                if (days.day_id == 4) {
                                    binding.wednesday.isChecked = true
                                    daysList.add(4)
                                }
                                if (days.day_id == 5) {
                                    binding.thursday.isChecked = true
                                    daysList.add(5)
                                }
                                if (days.day_id == 6) {
                                    binding.friday.isChecked = true
                                    daysList.add(6)
                                }
                            }
                        }

                        override fun onFailure(call: Call<DaysAtClinic>, t: Throwable) {

                        }

                    })
                }

            viewModel.getGovernoratesList("$BEARER$TOKEN").observe(viewLifecycleOwner) {
                it.enqueue(object : Callback<Governorates> {
                    override fun onResponse(
                        call: Call<Governorates>,
                        response: Response<Governorates>
                    ) {
                        governoratesModelList = response.body()!!.data
                        binding.spinnerAddress.adapter =
                            SpinnerGovernoratesAdapter(governoratesModelList)
                    }

                    override fun onFailure(call: Call<Governorates>, t: Throwable) {
                        Log.e(TAG, "onFailure: ${t.message}")
                    }

                })
            }
            viewModel.getAreaList("$BEARER$TOKEN").observe(viewLifecycleOwner) {
                it.enqueue(object : Callback<Araes> {
                    override fun onResponse(
                        call: Call<Araes>,
                        response: Response<Araes>
                    ) {
                        areaModelList = response.body()!!.data!!
                        binding.spinnerArea.adapter =
                            SpinnerAreaAdapter(areaModelList)
                    }

                    override fun onFailure(call: Call<Araes>, t: Throwable) {
                        Log.e(TAG, "onFailure: ${t.message}")
                    }

                })
            }
            binding.seekArc.setOnSeekArcChangeListener(object : OnSeekArcChangeListener {
                override fun onProgressChanged(
                    seekArc: SeekArc?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    binding.price.text = seekArc?.progress.toString()
                }

                override fun onStartTrackingTouch(seekArc: SeekArc?) {

                }

                override fun onStopTrackingTouch(seekArc: SeekArc?) {
                }

            })
            binding.saturday.setOnClickListener {
                addOrRemoveDay(0)
            }
            binding.sunday.setOnClickListener {
                addOrRemoveDay(1)
            }
            binding.monday.setOnClickListener {
                addOrRemoveDay(2)
            }
            binding.tuesday.setOnClickListener {
                addOrRemoveDay(3)
            }
            binding.wednesday.setOnClickListener {
                addOrRemoveDay(4)
            }
            binding.thursday.setOnClickListener {
                addOrRemoveDay(5)
            }
            binding.friday.setOnClickListener {
                addOrRemoveDay(6)
            }

            binding.done.setOnClickListener {
                viewModel.postClinic(
                    "$BEARER$TOKEN",
                    AddClinics(
                        name = binding.clinicName.text.toString(),
                        address = binding.address.text.toString(),
                        reservation_time_start = binding.reservationTimeStart.text.toString(),
                        reservation_time_end = binding.reservationTimeEnd.text.toString(),
                        opening_time = binding.openingTime.text.toString(),
                        closing_time = binding.closingTime.text.toString(),
                        overview = binding.overview.text.toString(),
                        governorate_id = governoratesModel,
                        area_id = areaModel,
                        doctor_id = doctor_id,
                        price = binding.price.text.toString()
                    )


                ).observe(viewLifecycleOwner) {
                    it.enqueue(object : Callback<Message> {
                        override fun onResponse(call: Call<Message>, response: Response<Message>) {
                            Toast.makeText(
                                requireActivity(),
                                "${response.body()?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onFailure(call: Call<Message>, t: Throwable) {

                        }

                    })
                }
                for (i in daysList) {
                    addDay(id = i/*id of day*/, token = "$BEARER$TOKEN")
                }
                for (i in 0..6) {
                    if (!daysList.contains(i)) {
                        deleteDay(id = i, token = "$BEARER$TOKEN")
                    }
                }
            }

        }

        val mcurrentTime: Calendar = Calendar.getInstance()
        val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute: Int = mcurrentTime.get(Calendar.MINUTE)

        binding.reservationTimeStart.setOnClickListener {
            mTimePicker =
                TimePickerDialog(requireActivity(), object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(
                        timePicker: TimePicker?,
                        selectedHour: Int,
                        selectedMinute: Int
                    ) {
                        binding.reservationTimeStart.setText("$selectedHour:$selectedMinute")
                    }
                }, hour, minute, false)
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }

        binding.reservationTimeEnd.setOnClickListener {
            mTimePicker =
                TimePickerDialog(requireActivity(), object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(
                        timePicker: TimePicker?,
                        selectedHour: Int,
                        selectedMinute: Int
                    ) {
                        binding.reservationTimeEnd.setText("$selectedHour:$selectedMinute")
                    }
                }, hour, minute, false)
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }

        binding.openingTime.setOnClickListener {
            mTimePicker =
                TimePickerDialog(requireActivity(), object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(
                        timePicker: TimePicker?,
                        selectedHour: Int,
                        selectedMinute: Int
                    ) {
                        binding.openingTime.setText("$selectedHour:$selectedMinute")
                    }
                }, hour, minute, false)
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }

        binding.closingTime.setOnClickListener {
            mTimePicker =
                TimePickerDialog(requireActivity(), object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(
                        timePicker: TimePicker?,
                        selectedHour: Int,
                        selectedMinute: Int
                    ) {
                        binding.closingTime.setText("$selectedHour:$selectedMinute")
                    }
                }, hour, minute, false)
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }

        binding.spinnerAddress.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                    try {
                        governoratesModel = governoratesModelList[i].id.toString()
                    } catch (_: IndexOutOfBoundsException) {
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    try {
                        governoratesModel = governoratesModelList[0].id.toString()
                    } catch (_: IndexOutOfBoundsException) {
                    }
                }

            }

        binding.spinnerArea.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                    try {
                        areaModel = areaModelList[i].id.toString()
                    } catch (_: IndexOutOfBoundsException) {
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    try {
                        areaModel = areaModelList[0].id.toString()
                    } catch (_: IndexOutOfBoundsException) {
                    }

                }

            }


        binding.spinnerAddress.setSpinnerEventsListener(object :
            OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                binding.spinnerAddress.background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.bg_spinner_down
                )
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                binding.spinnerAddress.background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.bg_spinner_up
                )
            }

        })
        binding.spinnerArea.setSpinnerEventsListener(object :
            OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                binding.spinnerArea.background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.bg_spinner_down
                )
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                binding.spinnerArea.background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.bg_spinner_up
                )
            }

        })

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun deleteDay(id: Int, token: String) {
        viewModel.deleteDoctorDaysAtClinic(
            token,
            daysAtClinicModel[daysAtClinicModel.indexOf(
                DaysAtClinicModel(
                    0,
                    id,
                    0,
                    "",
                    0,
                    "",
                    0
                )
            )].id.toString()
        ).observe(viewLifecycleOwner) {
            it.clone().enqueue(object : Callback<Message> {
                override fun onResponse(
                    call: Call<Message>,
                    response: Response<Message>
                ) {
                    Log.e(TAG, "onResponse: ${response.body()?.message}")
                }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
        }
    }

    private fun addDay(id: Int, token: String) {
        if (id in 0..6) {
            viewModel.postDoctorDaysAtClinic(
                token = token,
                daysAtClinicModel = DaysAtClinicModel(
                    clinic_id = 1,
                    day_id = id,
                    doctor_id = 1,
                    from_time = "00:15",
                    to_time = "12:21",
                    id = -1,
                    user_id = -1
                )
            ).observe(viewLifecycleOwner) {
                it.clone().enqueue(object : Callback<Message> {
                    override fun onResponse(
                        call: Call<Message>,
                        response: Response<Message>
                    ) {
                        Toast.makeText(
                            requireActivity(),
                            "onResponse ${response.body()?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Toast.makeText(
                            requireActivity(),
                            "onFailure ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            }
        } else {
            Toast.makeText(requireActivity(), "${id}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addOrRemoveDay(index: Int) {
        if (daysList.contains(index)) {
            daysList.remove(index)
        } else {
            daysList.add(index)
        }
    }
}