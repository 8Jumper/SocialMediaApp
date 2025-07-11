package com.example.socialmediaapp.ui.screens.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmediaapp.data.model.Todo
import com.example.socialmediaapp.data.model.User
import com.example.socialmediaapp.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel(private val repository: PostRepository) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchUserData(userId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _user.value = repository.getUser(userId)
                _todos.value = repository.getTodos(userId)
            } catch (e: Exception) {
                _user.value = null
                _todos.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}