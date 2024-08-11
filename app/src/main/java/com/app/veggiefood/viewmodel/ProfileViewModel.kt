package com.app.veggiefood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.veggiefood.model.request.DeleteUserRequest
import com.app.veggiefood.model.request.LoginRequest
import com.app.veggiefood.model.request.ProfileRequest
import com.app.veggiefood.model.response.BaseResponse
import com.app.veggiefood.model.response.LoginResponse
import com.app.veggiefood.model.response.ProfileResponse
import com.app.veggiefood.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val profileRepository: ProfileRepository) : ViewModel() {
    val isLoading = MutableLiveData(false)
    val profileLiveData = MutableLiveData<ProfileResponse>()
    val updateProfileLiveData = MutableLiveData<BaseResponse>()
    val deleteUserLiveData = MutableLiveData<BaseResponse>()

    fun getProfile(
        id
        : String
    ) {
        viewModelScope.launch {
            isLoading.postValue(true)
            profileRepository.getProfile(id).let {
                if (it.body() != null) {
                    profileLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }


    fun updateProfile(
        id
        : String,
        request: ProfileRequest
    ) {
        viewModelScope.launch {
            isLoading.postValue(true)
            profileRepository.updateProfile(id, request).let {
                if (it.body() != null) {
                    updateProfileLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }

    fun deleteAccount(
        request: DeleteUserRequest
    ) {
        viewModelScope.launch {
            isLoading.postValue(true)
            profileRepository.deleteUser(request).let {
                if (it.body() != null) {
                    deleteUserLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }
}