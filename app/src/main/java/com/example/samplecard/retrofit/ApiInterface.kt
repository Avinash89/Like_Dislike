package com.example.samplecard.retrofit

import com.example.samplecard.model.UserResponseList
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("?results=10")
    fun getServices(): Call<UserResponseList>

}