package com.doubleclick.doctorapp.android.Home.DoctorConfig

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.doubleclick.doctorapp.android.Adapters.PixAdapter
import com.doubleclick.doctorapp.android.Home.Pix.PixCameraActivity
import com.doubleclick.doctorapp.android.Home.fragment.fragmentBody
import com.doubleclick.doctorapp.android.Home.options
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.databinding.FragmentRouteBinding
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.pixFragment


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

        binding.video.setOnClickListener {
            startActivity(Intent(requireActivity(), PixCameraActivity::class.java))
        }

       /* pixFragment(options) {
            when (it.status) {
                PixEventCallback.Status.SUCCESS -> {

                }//use results as it.data

                PixEventCallback.Status.BACK_PRESSED -> {

                }// back pressed called

            }
        }*/

    }
}
