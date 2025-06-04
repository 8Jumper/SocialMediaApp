package com.example.socialmediaapp.ui.screens.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.socialmediaapp.data.DataStoreManager
import com.example.socialmediaapp.data.repository.PostRepository
import com.example.socialmediaapp.data.network.RetrofitInstance

class PostDetailViewModelFactory(private val repository: PostRepository, private val dataStoreManager: DataStoreManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PostDetailViewModel(repository, dataStoreManager) as T
    }
}