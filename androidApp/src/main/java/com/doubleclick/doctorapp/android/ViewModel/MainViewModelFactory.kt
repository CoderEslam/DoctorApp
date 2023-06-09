package com.doubleclick.doctorapp.android.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot

class MainViewModelFactory(private val repository: RepositoryRemot) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}