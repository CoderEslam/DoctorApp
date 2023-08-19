package com.doubleclick.doctorapp.android.Home.DoctorConfig

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.databinding.FragmentRouteBinding


class RouteFragment : Fragment() {

    private lateinit var binding: FragmentRouteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRouteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addClinic.setOnClickListener {
            findNavController().navigate(RouteFragmentDirections.actionRouteFragmentToDoctorClinicsFragment())
        }
        binding.doctorAssistants.setOnClickListener {
            findNavController().navigate(RouteFragmentDirections.actionRouteFragmentToAssistantsFragment())
        }

        binding.doctorInfo.setOnClickListener {
            findNavController().navigate(RouteFragmentDirections.actionRouteFragmentToDoctorInfoFragment())
        }
    }
}