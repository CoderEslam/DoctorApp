package com.doubleclick.doctorapp.android.Home.DoctorConfig

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doubleclick.doctorapp.android.Adapters.PixAdapter
import com.doubleclick.doctorapp.android.Adapters.RouteAdapter
import com.doubleclick.doctorapp.android.Home.Pix.PixCameraActivity
import com.doubleclick.doctorapp.android.Home.fragment.fragmentBody
import com.doubleclick.doctorapp.android.Home.options
import com.doubleclick.doctorapp.android.Model.Role
import com.doubleclick.doctorapp.android.Model.RouteModel
import com.doubleclick.doctorapp.android.OnRoute
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.databinding.FragmentRouteBinding
import com.doubleclick.doctorapp.android.utils.SessionManger.getRole
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.pixFragment
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RouteFragment : Fragment(), OnRoute {

    private lateinit var binding: FragmentRouteBinding
    private var role = ""
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

        binding.rvRoute.adapter = RouteAdapter(
            listOf(
                RouteModel("Add Clinic", 0),
                RouteModel("Doctor Assistants", 1),
                RouteModel("Doctor Info", 2),
                RouteModel("Reservation", 3),
                RouteModel("Old Reservation", 4)
            ),
            this@RouteFragment
        )

        binding.video.setOnClickListener {
            findNavController().navigate(RouteFragmentDirections.actionRouteFragmentToPixVideoFragment())
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            role = requireActivity().getRole().toString()
        }
    }

    override fun onClick(tag: Int) {
        when (tag) {
            0 -> {
                if (role == Role.Doctor.role) {
                    findNavController().navigate(RouteFragmentDirections.actionRouteFragmentToDoctorClinicsFragment())
                } else {
                    Toast.makeText(requireActivity(), "you don't have access", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            1 -> {
                if (role == Role.Doctor.role) {
                    findNavController().navigate(RouteFragmentDirections.actionRouteFragmentToAssistantsFragment())
                } else {
                    Toast.makeText(requireActivity(), "you don't have access", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            2 -> {
                if (role == Role.Doctor.role) {
                    findNavController().navigate(RouteFragmentDirections.actionRouteFragmentToDoctorInfoFragment())
                } else {
                    Toast.makeText(requireActivity(), "you don't have access", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            3 -> {
                findNavController().navigate(RouteFragmentDirections.actionRouteFragmentToReservationsFragment())
            }
            4 -> {
                findNavController().navigate(RouteFragmentDirections.actionRouteFragmentToMyPatientsFragment())
            }
        }
    }
}


