package com.example.samplecard.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samplecard.model.UserResponseList
import com.example.samplecard.repository.MainActivityRepository

class MainActivityViewModel : ViewModel() {
    var servicesLiveData: MutableLiveData<UserResponseList>? = null

    fun getUser(): LiveData<UserResponseList>? {
        servicesLiveData = MainActivityRepository.getServicesApiCall()
        return servicesLiveData
    }

    fun insertData(
        context: Context,
        udid: String,
        username: String,
        imageUrl: String,
        status: Boolean
    ) {
        MainActivityRepository.insertData(context, udid, username, imageUrl, status)
    }
}