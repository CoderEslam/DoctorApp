package com.doubleclick.doctorapp.android.Home.DoctorConfig

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.databinding.FragmentPriceBinding
import com.doubleclick.doctorapp.android.views.SeekArc.SeekArc
import com.doubleclick.doctorapp.android.views.SeekArc.`interface`.OnSeekArcChangeListener


class PriceFragment : Fragment() {

    private lateinit var binding: FragmentPriceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPriceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.seekArc.setOnSeekArcChangeListener(object : OnSeekArcChangeListener {
            override fun onProgressChanged(seekArc: SeekArc?, progress: Int, fromUser: Boolean) {
                binding.price.text = seekArc?.progress.toString()
            }

            override fun onStartTrackingTouch(seekArc: SeekArc?) {

            }

            override fun onStopTrackingTouch(seekArc: SeekArc?) {
            }

        })

    }

}