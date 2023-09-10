package com.doubleclick.doctorapp.android.Home.Filter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.doubleclick.doctorapp.android.Adapters.DoctorsAdapter
import com.doubleclick.doctorapp.android.FavoritesDoctor
import com.doubleclick.doctorapp.android.Model.Doctor.DoctorsList
import com.doubleclick.doctorapp.android.Model.Doctor.SearchDoctor
import com.doubleclick.doctorapp.android.Model.Favorite.FavoriteModel
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.ActivityFilterBinding
import com.doubleclick.doctorapp.android.utils.Constants.BEARER
import com.doubleclick.doctorapp.android.utils.SessionManger.getToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilterActivity : AppCompatActivity(), FavoritesDoctor {

    private lateinit var binding: ActivityFilterBinding

    private lateinit var viewModel: MainViewModel

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this@FilterActivity,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]

        GlobalScope.launch(Dispatchers.Main) {
            viewModel.searchDoctor(
                token = BEARER + getToken().toString(),
                searchDoctor = SearchDoctor(
                    term = intent?.extras?.getString("searchItem").toString()
                )
            ).observe(this@FilterActivity) {
                it.clone().enqueue(object : Callback<DoctorsList> {
                    override fun onResponse(
                        call: Call<DoctorsList>,
                        response: Response<DoctorsList>
                    ) {
                        binding.homeRecyclerViewPopular.adapter =
                            response.body()?.data?.let { it1 -> DoctorsAdapter(it1, this@FilterActivity) }
                    }

                    override fun onFailure(call: Call<DoctorsList>, t: Throwable) {

                    }

                })
            }
        }

    }

    override fun deleteFavorite(favoriteModel: FavoriteModel, id: String) {

    }

    override fun setFavorite(id: String) {

    }
}