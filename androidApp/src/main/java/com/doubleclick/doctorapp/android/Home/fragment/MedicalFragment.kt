package com.doubleclick.doctorapp.android.Home.fragment

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.databinding.FragmentMedicalBinding
import com.iceteck.silicompressorr.Util.getFilePath
import java.io.File


class MedicalFragment : Fragment() {


    private lateinit var binding: FragmentMedicalBinding

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
        binding = FragmentMedicalBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.save.setOnClickListener {
            if (binding.viewSignature.getTouched()) {
                binding.viewSignature.save(
                    getFilePath(), true, 10
                )
                Toast.makeText(requireActivity(), "Saved", Toast.LENGTH_SHORT).show()
            }

        }

        binding.clear.setOnClickListener {
            binding.viewSignature.clear();
            Toast.makeText(requireActivity(), "Clear", Toast.LENGTH_SHORT).show()
        }


    }

    private fun getFilePath(): String {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath,
            "Images"
        )
        if (!file.exists()) {
            file.mkdirs()
        }
        return "${file.absoluteFile}" + File.separator + System.currentTimeMillis() + ".png"
    }
}