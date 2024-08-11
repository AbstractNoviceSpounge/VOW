package com.app.veggiefood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.veggiefood.model.request.LoginRequest
import com.app.veggiefood.model.request.SignupRequest
import com.app.veggiefood.model.response.BaseResponse
import com.app.veggiefood.model.response.LoginResponse
import com.app.veggiefood.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(val signupRepository: SignupRepository): ViewModel() {
    val isLoading = MutableLiveData(false)
    val signupLiveData = MutableLiveData<BaseResponse>()

    fun doSignup(request: SignupRequest) {
        viewModelScope.launch {
            isLoading.postValue(true)
            signupRepository.doRegister(request).let {
                if (it.body() != null) {
                    signupLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }
}