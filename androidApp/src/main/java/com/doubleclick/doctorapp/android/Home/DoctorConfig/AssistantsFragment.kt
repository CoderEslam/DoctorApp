package com.doubleclick.doctorapp.android.Home.DoctorConfig

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doubleclick.doctorapp.android.Adapters.AdapterDoctorAssistant
import com.doubleclick.doctorapp.android.DeleteAssistant
import com.doubleclick.doctorapp.android.Model.Assistants.AddAssistants
import com.doubleclick.doctorapp.android.Model.Assistants.AssistantsList
import com.doubleclick.doctorapp.android.Model.Assistants.AssistantsModel
import com.doubleclick.doctorapp.android.Model.Message
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.api.RetrofitInstance
import com.doubleclick.doctorapp.android.databinding.FragmentAssistantsBinding
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.SessionManger.getDoctorId
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AssistantsFragment : Fragment(), DeleteAssistant {

    private lateinit var binding: FragmentAssistantsBinding;
    private lateinit var viewModel: MainViewModel
    private var assistantsModelList: MutableList<AssistantsModel> = mutableListOf()
    private lateinit var adapterDoctorAssistant: AdapterDoctorAssistant
    private val TAG = "AssistantsFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAssistantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this, MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]
        binding.addAssistant.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                viewModel.postAssistants(
                    "$BEARER${requireActivity().getToken()}",
                    AddAssistants(
                        name = binding.assistantName.text.toString(),
                        phone = binding.assistantPhone.text.toString(),
                        doctor_id = requireActivity().getDoctorId().toString()
                    )
                ).observe(viewLifecycleOwner) {
                    it.clone().enqueue(object : Callback<Message> {
                        override fun onResponse(call: Call<Message>, response: Response<Message>) {
                            if (!response.body()?.message.isNullOrEmpty()) {
                                binding.assistantName.setText("")
                                binding.assistantPhone.setText("")
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
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        getAssistant()
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