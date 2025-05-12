package com.example.socialmediaapp.data.repository

import com.example.socialmediaapp.data.model.Post
import com.example.socialmediaapp.data.model.User
import com.example.socialmediaapp.data.network.RetrofitInstance


class PostRepository {
    private val api = RetrofitInstance.api

    suspend fun getPosts(): List<Post> = api.getPosts()
    suspend fun getUsers(): List<User> = api.getUsers()
}
