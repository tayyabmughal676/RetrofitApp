package com.smartcenter.retrofitapp.Network

import com.smartcenter.retrofitapp.Models.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    fun getUsers(): Call<MutableList<User>>
}