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
import com.doubleclick.doctorapp.android.FavoritesDoctor
import com.doubleclick.doctorapp.android.Home.Filter.FilterActivity
import com.doubleclick.doctorapp.android.Model.Clinic.ClinicList
import com.doubleclick.doctorapp.android.Model.Doctor.DoctorId
import com.doubleclick.doctorapp.android.Model.Doctor.DoctorsList
import com.doubleclick.doctorapp.android.Model.Favorite.FavoriteModel
import com.doubleclick.doctorapp.android.Model.MedicalAdvice.MedicalAdvice
import com.doubleclick.doctorapp.android.Model.Message
import com.doubleclick.doctorapp.android.Model.Specialization.SpecializationList
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.Search
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.api.RetrofitInstance
import com.doubleclick.doctorapp.android.databinding.FragmentHomeBinding
import com.doubleclick.doctorapp.android.utils.Constants
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.SessionManger.getId
import com.doubleclick.doctorapp.android.utils.SessionManger.getName
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import com.doubleclick.multisearchview.MultiSearchView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), CreatePDF, Search, FavoritesDoctor {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainViewModel
    private val TAG = "HomeFragment"
    private var token = ""
    private lateinit var specializationList: SpecializationList


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

        viewModel = ViewModelProvider(
            this@HomeFragment,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            token = BEARER + requireActivity().getToken().toString()
            binding.userName.text = buildString {
                append(getString(R.string.hi))
                append(" ")
                append(requireActivity().getName())
                append("!")
            }
            Glide.with(requireActivity()).load(
                "${Constants.IMAGE_URL_USERS}${requireActivity().getName()}_${requireActivity().getId()}.jpg"
            ).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imageProfile)

            RetrofitInstance.api.getMedicalAdvices(token = token)
                .clone()
                .enqueue(object : Callback<MedicalAdvice> {
                    override fun onResponse(
                        call: Call<MedicalAdvice>,
                        response: Response<MedicalAdvice>
                    ) {
                        binding.homeRecyclerViewVideo.adapter =
                            response.body()?.data?.let { DoctorVideoAdapter(it.toMutableList()) };
                    }

                    override fun onFailure(call: Call<MedicalAdvice>, t: Throwable) {

                    }

                })


            binding.animationView.visibility = View.VISIBLE
            viewModel.getDoctorsList(token = token)
                .observe(viewLifecycleOwner) {
                    it.enqueue(object : Callback<DoctorsList> {
                        @SuppressLint("NotifyDataSetChanged")
                        override fun onResponse(
                            call: Call<DoctorsList>,
                            response: Response<DoctorsList>
                        ) {
                            if (response.body() != null) {

                                binding.homeRecyclerViewPopular.adapter =
                                    response.body()?.data?.let { doctors ->
                                        DoctorsAdapter(
                                            doctors,
                                            this@HomeFragment
                                        )
                                    }


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

            viewModel.getSpecializations(token = token)
                .observe(viewLifecycleOwner) {
                    it.enqueue(object : Callback<SpecializationList> {
                        override fun onResponse(
                            call: Call<SpecializationList>, response: Response<SpecializationList>
                        ) {
                            if (response.body() != null) {
                                specializationList = response.body()!!
                                binding.homeRecyclerViewSpacification.adapter =
                                    specializationList.data?.let { it1 ->
                                        SpecializationAdapter(
                                            this@HomeFragment,
                                            it1
                                        )
                                    }
                            }
                        }

                        override fun onFailure(call: Call<SpecializationList>, t: Throwable) {

                        }

                    })
                }
            viewModel.getClinicList(token).observe(viewLifecycleOwner) {
                it.clone().enqueue(object : Callback<ClinicList> {
                    override fun onResponse(
                        call: Call<ClinicList>,
                        response: Response<ClinicList>
                    ) {
                        Log.e(TAG, "onResponse: ${response.body()?.data.toString()}")
                    }

                    override fun onFailure(call: Call<ClinicList>, t: Throwable) {

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

//                PDFConverter.createPdf(
//                    requireContext(),
//                    "",
//                    requireActivity(),
//                    this@HomeFragment
//                )
            }

            override fun onTextChanged(index: Int, s: CharSequence) {
                Log.e("TEST", "changed: index: $index, query: $s")
            }

            override fun onSearchComplete(index: Int, s: CharSequence) {
                val intent = Intent(requireActivity(), FilterActivity::class.java)
                intent.putExtra("searchItem", s.toString())
                startActivity(intent)
                Log.e("TEST", "complete: index: $index, query: $s")
            }

            override fun onSearchItemRemoved(index: Int) {
                Log.e("TEST", "remove: index: $index")
            }

        })
    }



    override fun search(name: String) {
        val intent = Intent(requireActivity(), FilterActivity::class.java)
        intent.putExtra("searchItem", name)
        startActivity(intent)
    }

    override fun deleteFavorite(favoriteModel: FavoriteModel, id: String) {

    }

    override fun setFavorite(id: String) {
        viewModel.putFavoriteDoctor(token = token, doctor_id = DoctorId(id))
            .observe(viewLifecycleOwner) {
                it.clone().enqueue(object : Callback<Message> {
                    override fun onResponse(call: Call<Message>, response: Response<Message>) {
                        Snackbar.make(
                            requireView(),
                            response.body()?.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    override fun onFailure(call: Call<Message>, t: Throwable) {

                    }

                })
            }
    }

    override fun onStartPDF() {

    }

    override fun onFinishPDF() {

    }

}