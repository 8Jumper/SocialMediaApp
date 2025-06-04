package com.example.socialmediaapp.ui.screens.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.socialmediaapp.data.repository.PostRepository

class UserDetailViewModelFactory(private val repository: PostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserDetailViewModel(repository) as T
    }
}