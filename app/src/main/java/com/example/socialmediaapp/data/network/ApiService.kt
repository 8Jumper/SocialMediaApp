package com.example.socialmediaapp.data.network

import retrofit2.http.GET
import com.example.socialmediaapp.data.model.Post
import com.example.socialmediaapp.data.model.Todo
import com.example.socialmediaapp.data.model.User
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Post

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): User

    @GET("todos")
    suspend fun getTodos(@Query("userId") userId: Int): List<Todo>
}