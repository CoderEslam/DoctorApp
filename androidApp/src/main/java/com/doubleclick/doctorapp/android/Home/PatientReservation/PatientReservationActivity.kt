package com.doubleclick.doctorapp.android.Home.PatientReservation

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.doubleclick.doctorapp.android.Adapters.SpinnerAdapter
import com.doubleclick.doctorapp.android.Adapters.SpinnerFamilyMemberAdapter
import com.doubleclick.doctorapp.android.Adapters.SpinnerGovernoratesAdapter
import com.doubleclick.doctorapp.android.Model.*
import com.doubleclick.doctorapp.android.Model.Area.Araes
import com.doubleclick.doctorapp.android.Model.Governorates.Governorates
import com.doubleclick.doctorapp.android.Model.Governorates.GovernoratesModel
import com.doubleclick.doctorapp.android.Model.Patient.PatientModel
import com.doubleclick.doctorapp.android.Model.Patient.PatientsList
import com.doubleclick.doctorapp.android.Model.PatientReservations.PatientReservationsModel
import com.doubleclick.doctorapp.android.Model.PatientReservations.PostPatientReservations
import com.doubleclick.doctorapp.android.OnSpinnerEventsListener
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.ActivityPatientReservationBinding
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.SessionManger.getId
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import com.doubleclick.doctorapp.android.utils.getDays
import com.doubleclick.doctorapp.android.utils.getMonth
import com.doubleclick.doctorapp.android.utils.getYears
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PatientReservationActivity : AppCompatActivity() {
    private val TAG = "PatientReservationActiv"

    private lateinit var binding: ActivityPatientReservationBinding
    private var year: String = ""
    private var month: String = ""
    private var day: String = ""
    private var reason_visit: String = ""
    private var governoratesModel: String = ""
    private var areaModel: String = ""
    private var TOKEN: String = ""
    private lateinit var viewModel: MainViewModel
    private var governoratesModelList: List<GovernoratesModel> = mutableListOf()
    private var areaModelList: List<GovernoratesModel> = mutableListOf()
    private var reasonModelList: List<String> = mutableListOf()
    private var patientModelList: MutableList<PatientModel> = mutableListOf()
    private lateinit var patientModel: PatientModel
    private lateinit var mTimePicker: TimePickerDialog

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]
        reasonModelList = listOf(
            "Check up",
            "Doctor consultation"
        )
        binding.spinnerYear.adapter = SpinnerAdapter(this@PatientReservationActivity, getYears())
        binding.spinnerMonth.adapter = SpinnerAdapter(this@PatientReservationActivity, getMonth())
        binding.spinnerDay.adapter = SpinnerAdapter(this@PatientReservationActivity, getDays())
        binding.spinnerReason.adapter =
            SpinnerAdapter(this@PatientReservationActivity, reasonModelList)

        GlobalScope.launch(Dispatchers.Main) {
            TOKEN = getToken().toString()
            viewModel.getFamilyMemberPatient(
                "${BEARER}$TOKEN"
            ).observe(this@PatientReservationActivity) {
                it.clone().enqueue(object : Callback<PatientsList> {
                    override fun onResponse(
                        call: Call<PatientsList>,
                        response: Response<PatientsList>
                    ) {
                        if (response.body()?.data != null) {
                            patientModelList = response.body()?.data?.toMutableList()!!
                            setupFamilyMember(patientModelList)
                        }
                    }

                    override fun onFailure(call: Call<PatientsList>, t: Throwable) {

                    }

                })
            }

            viewModel.getGovernoratesList("${BEARER}$TOKEN")
                .observe(this@PatientReservationActivity) {
                    it.enqueue(object : Callback<Governorates> {
                        override fun onResponse(
                            call: Call<Governorates>,
                            response: Response<Governorates>
                        ) {
                            governoratesModelList = response.body()!!.data
                            //                            binding.spinnerCity.adapter =
                            //                                SpinnerGovernoratesAdapter(
                            //                                    governoratesModelList
                            //                                )
                        }

                        override fun onFailure(call: Call<Governorates>, t: Throwable) {
                            Log.e(TAG, "onFailure: ${t.message}")
                        }

                    })
                }
            viewModel.getAreaList("${BEARER}$TOKEN")
                .observe(this@PatientReservationActivity) {
                    it.enqueue(object : Callback<Araes> {
                        override fun onResponse(
                            call: Call<Araes>,
                            response: Response<Araes>
                        ) {
                            areaModelList = response.body()!!.data
                            binding.spinnerArea.adapter =
                                SpinnerGovernoratesAdapter(
                                    areaModelList
                                )
                        }

                        override fun onFailure(call: Call<Araes>, t: Throwable) {
                            Log.e(TAG, "onFailure: ${t.message}")
                        }

                    })
                }
        }


        binding.spinnerYear.setSpinnerEventsListener(object :
            OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                binding.spinnerYear.background =
                    ContextCompat.getDrawable(
                        this@PatientReservationActivity,
                        R.drawable.spinner_down
                    )
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                binding.spinnerYear.background = ContextCompat.getDrawable(
                    this@PatientReservationActivity,
                    R.drawable.spinner_up
                )
            }

        })

        binding.spinnerMonth.setSpinnerEventsListener(object :
            OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                binding.spinnerMonth.background =
                    ContextCompat.getDrawable(
                        this@PatientReservationActivity,
                        R.drawable.spinner_down
                    )
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                binding.spinnerMonth.background = ContextCompat.getDrawable(
                    this@PatientReservationActivity,
                    R.drawable.spinner_up
                )
            }

        })


        binding.spinnerReason.setSpinnerEventsListener(object :
            OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                binding.spinnerReason.background =
                    ContextCompat.getDrawable(
                        this@PatientReservationActivity,
                        R.drawable.bg_spinner_down
                    )
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                binding.spinnerReason.background = ContextCompat.getDrawable(
                    this@PatientReservationActivity,
                    R.drawable.bg_spinner_up
                )
            }

        })

        binding.spinnerYear.setSpinnerEventsListener(object :
            OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                binding.spinnerYear.background =
                    ContextCompat.getDrawable(
                        this@PatientReservationActivity,
                        R.drawable.spinner_down
                    )
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                binding.spinnerYear.background = ContextCompat.getDrawable(
                    this@PatientReservationActivity,
                    R.drawable.spinner_up
                )
            }

        })

        binding.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                year = getYears()[i]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                year = getYears()[0]
            }

        }

        binding.spinnerMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                month = getMonth()[i]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                month = getMonth()[0]
            }

        }

        binding.spinnerDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                day = getDays()[i]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                day = getDays()[0]
            }

        }

        binding.spinnerReason.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                try {
                    reason_visit = reasonModelList[i]
                } catch (_: IndexOutOfBoundsException) {
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                try {
                    reason_visit = reasonModelList[0]
                } catch (_: IndexOutOfBoundsException) {
                }
            }

        }
        binding.spinnerArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

        binding.Continue.setOnClickListener {
            viewModel.postPatientReservations(
                "${BEARER}$TOKEN",
                PostPatientReservations(
                    age = "$year/$month/$day",
                    clinic_id = intent.extras?.getString("clinic_id")!!,
                    doctor_id = intent.extras?.getString("doctor_id")!!,
                    kind = if (binding.radioBtnMale.isChecked) "male" else if (binding.radioBtnFemale.isChecked) "female" else "",
                    patient_id = patientModel.id.toString(),
                    patient_phone = binding.patientPhone.text.toString(),
                    reservation_date = binding.reservationDate.text.toString(),
                    type = reason_visit,
                )
            ).observe(this@PatientReservationActivity) {
                it.clone().enqueue(object : Callback<Message> {
                    override fun onResponse(
                        call: Call<Message>,
                        response: Response<Message>
                    ) {
                        Toast.makeText(
                            this@PatientReservationActivity,
                            response.body()?.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Log.e(TAG, "onFailure: ${t.message}")
                    }

                })
            }
        }

        val mcurrentTime: Calendar = Calendar.getInstance()
        val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute: Int = mcurrentTime.get(Calendar.MINUTE)

        binding.reservationDate.setOnClickListener {
            mTimePicker =
                TimePickerDialog(
                    this@PatientReservationActivity,
                    object : TimePickerDialog.OnTimeSetListener {
                        override fun onTimeSet(
                            timePicker: TimePicker?,
                            selectedHour: Int,
                            selectedMinute: Int
                        ) {
                            binding.reservationDate.setText("$selectedHour:$selectedMinute")
                        }
                    },
                    hour,
                    minute,
                    false
                )
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }
    }

    fun setupFamilyMember(patientModelList: MutableList<PatientModel>) {
        binding.spinnerPatientName.adapter = SpinnerFamilyMemberAdapter(patientModelList)
        binding.spinnerPatientName.setSpinnerEventsListener(object :
            OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                binding.arrowPatientName.rotation = 180f
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                binding.arrowPatientName.rotation = 0f
            }

        })

        binding.spinnerPatientName.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                    patientModel = patientModelList[i]
                    Toast.makeText(
                        this@PatientReservationActivity,
                        patientModel.id.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    patientModel = patientModelList[0]
                    Toast.makeText(
                        this@PatientReservationActivity,
                        patientModel.id.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
    }
}