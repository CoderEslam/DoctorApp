package com.doubleclick.doctorapp.android.Home.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.doubleclick.doctorapp.android.Adapters.DoctorVideoAdapter
import com.doubleclick.doctorapp.android.Adapters.DoctorsAdapter
import com.doubleclick.doctorapp.android.Adapters.SpecializationAdapter
import com.doubleclick.doctorapp.android.CreatePDF
import com.doubleclick.doctorapp.android.Home.Filter.FilterActivity
import com.doubleclick.doctorapp.android.Model.Doctor.DoctorsList
import com.doubleclick.doctorapp.android.Model.Specialization.Specialization
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.FragmentHomeBinding
import com.doubleclick.doctorapp.android.utils.Constants
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.SessionManger.getId
import com.doubleclick.doctorapp.android.utils.SessionManger.getName
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import com.doubleclick.multisearchview.MultiSearchView
import com.doubleclick.smarthealth.android.pdf.PDFConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), CreatePDF {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainViewModel
    private val TAG = "HomeFragment"
    private lateinit var specializationList: Specialization

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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeRecyclerViewVideo.adapter = DoctorVideoAdapter();

        viewModel = ViewModelProvider(
            this@HomeFragment,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            binding.userName.text = buildString {
                append(getString(R.string.hi))
                append(requireActivity().getName())
                append("!")
            }
            Glide.with(requireActivity()).load(
                "${Constants.IMAGE_URL_USERS}${requireActivity().getName()}_${requireActivity().getId()}.jpg"
            ).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imageProfile)


            binding.animationView.visibility = View.VISIBLE
            viewModel.getDoctorsList("$BEARER${requireActivity().getToken()}")
                .observe(viewLifecycleOwner) {
                    it.enqueue(object : Callback<DoctorsList> {
                        @SuppressLint("NotifyDataSetChanged")
                        override fun onResponse(
                            call: Call<DoctorsList>,
                            response: Response<DoctorsList>
                        ) {
                            if (response.body() != null) {

                                binding.homeRecyclerViewPopular.adapter =
                                    DoctorsAdapter(response.body()!!.data)


                                binding.animationView.visibility = View.GONE
                            }
                        }

                        override fun onFailure(call: Call<DoctorsList>, t: Throwable) {
                            Toast.makeText(
                                requireActivity(),
                                t.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
                }

            viewModel.getSpecializations("$BEARER${requireActivity().getToken()}")
                .observe(viewLifecycleOwner) {
                    it.enqueue(object : Callback<Specialization> {
                        override fun onResponse(
                            call: Call<Specialization>, response: Response<Specialization>
                        ) {
                            specializationList = response.body()!!
                            binding.homeRecyclerViewSpacification.adapter =
                                SpecializationAdapter(specializationList.data)

                        }

                        override fun onFailure(call: Call<Specialization>, t: Throwable) {

                        }

                    })
                }
        }


        binding.multiSearchView.setSearchViewListener(object :
            MultiSearchView.MultiSearchViewListener {
            override fun onItemSelected(index: Int, s: CharSequence) {
                val intent = Intent(requireActivity(), FilterActivity::class.java)
                intent.putExtra("searchItem", s.toString())
                startActivity(intent)
                Log.v("TEST", "onItemSelected: index: $index, query: $s")

                PDFConverter.createPdf(
                    requireContext(),
                    "",
                    requireActivity(),
                    this@HomeFragment
                )
            }

            override fun onTextChanged(index: Int, s: CharSequence) {
                Log.v("TEST", "changed: index: $index, query: $s")
            }

            override fun onSearchComplete(index: Int, s: CharSequence) {
                val intent = Intent(requireActivity(), FilterActivity::class.java)
                intent.putExtra("searchItem", s.toString())
                startActivity(intent)
                Log.v("TEST", "complete: index: $index, query: $s")
            }

            override fun onSearchItemRemoved(index: Int) {
                Log.v("TEST", "remove: index: $index")
            }

        })
    }

    override fun onStartCreating() {
    }

    override fun onStopCreating() {
    }

}