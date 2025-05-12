package com.example.socialmediaapp.data.network

import retrofit2.http.GET
import com.example.socialmediaapp.data.model.Post
import com.example.socialmediaapp.data.model.User

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("users")
    suspend fun getUsers(): List<User>
}
