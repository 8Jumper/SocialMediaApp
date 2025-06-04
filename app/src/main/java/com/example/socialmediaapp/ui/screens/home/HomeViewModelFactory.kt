package com.example.socialmediaapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.socialmediaapp.data.network.RetrofitInstance
import com.example.socialmediaapp.data.repository.PostRepository

class HomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(PostRepository(RetrofitInstance.api)) as T
    }
}