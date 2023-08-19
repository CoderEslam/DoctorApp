package com.doubleclick.doctorapp.android.Home.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.doubleclick.doctorapp.android.Adapters.FavoriteDoctorsAdapter
import com.doubleclick.doctorapp.android.BookingTime
import com.doubleclick.doctorapp.android.FavoritesDoctor
import com.doubleclick.doctorapp.android.Model.Favorite.FavoriteDoctor
import com.doubleclick.doctorapp.android.Model.Favorite.FavoriteModel
import com.doubleclick.doctorapp.android.Model.Message
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.FragmentFavoriteBinding
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavoriteFragment() : Fragment(), BookingTime, FavoritesDoctor {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: MainViewModel
    private val TAG = "FavoriteFragment"
    private var TOKEN = ""
    private lateinit var favoriteDoctorsAdapter: FavoriteDoctorsAdapter
    private var favoriteModelList: MutableList<FavoriteModel> = mutableListOf()

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
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            binding.animationView.visibility = View.VISIBLE
            TOKEN = requireActivity().getToken().toString()
            viewModel.getFavoriteDoctor("$BEARER${TOKEN}")
                .observe(viewLifecycleOwner) {
                    it.enqueue(object : Callback<FavoriteDoctor> {
                        override fun onResponse(
                            call: Call<FavoriteDoctor>,
                            response: Response<FavoriteDoctor>
                        ) {
                            favoriteModelList = response.body()?.data!!.toMutableList()
                            favoriteDoctorsAdapter = FavoriteDoctorsAdapter(
                                favoriteModelList,
                                this@FavoriteFragment,
                                this@FavoriteFragment
                            )
                            binding.animationView.visibility = View.GONE
                            binding.favoriteDoctors.adapter = favoriteDoctorsAdapter;
                            favoriteDoctorsAdapter.notifyDataSetChanged()
                            Log.e(TAG, "onResponse: ${response.body()?.data!!}")

                        }

                        override fun onFailure(call: Call<FavoriteDoctor>, t: Throwable) {
                            Log.e(TAG, "onFailure: ${t.message}")
                        }

                    })
                }
        }
    }


    override fun book() {
        Toast.makeText(requireActivity(), "Book", Toast.LENGTH_SHORT).show()
    }


    override fun deleteFavorite(favoriteModel: FavoriteModel, id: String) {
        viewModel.deleteFavoriteDoctor("$BEARER${TOKEN}", id).observe(viewLifecycleOwner) {
            it.clone().enqueue(object : Callback<Message> {
                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    favoriteModelList.remove(favoriteModel)
                    favoriteDoctorsAdapter.notifyItemRemoved(favoriteModelList.indexOf(favoriteModel))
                    Snackbar.make(
                        binding.root,
                        response.body()?.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        }
    }

    override fun setFavorite(id: String) {

    }


}