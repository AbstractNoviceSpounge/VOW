package com.app.veggiefood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.veggiefood.model.request.LoginRequest
import com.app.veggiefood.model.response.LoginResponse
import com.app.veggiefood.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginRepository: LoginRepository):ViewModel() {
    val isLoading = MutableLiveData(false)
    val loginLiveData = MutableLiveData<LoginResponse>()

    fun doLogin(request: LoginRequest) {
        viewModelScope.launch {
            isLoading.postValue(true)
            loginRepository.doLogin(request).let {
                if (it.body() != null) {
                    loginLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }

}