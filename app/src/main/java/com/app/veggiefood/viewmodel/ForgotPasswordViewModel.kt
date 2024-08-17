package com.app.veggiefood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.veggiefood.model.request.ForgotPasswordRequest
import com.app.veggiefood.model.request.LoginRequest
import com.app.veggiefood.model.response.ForgotPasswordResponse
import com.app.veggiefood.model.response.LoginResponse
import com.app.veggiefood.repository.ForgotPasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(val forgotPasswordRepository: ForgotPasswordRepository) :
    ViewModel() {
    val isLoading = MutableLiveData(false)
    val forgotPasswordLiveData = MutableLiveData<ForgotPasswordResponse>()

    fun doForgotPassword(request: ForgotPasswordRequest) {
        viewModelScope.launch {
            isLoading.postValue(true)
            forgotPasswordRepository.doForgotPasword(request).let {
                if (it.body() != null) {
                    forgotPasswordLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }

}