package com.app.veggiefood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.veggiefood.model.request.ChangePasswordRequest
import com.app.veggiefood.model.response.BaseResponse
import com.app.veggiefood.model.response.CategoryResponse
import com.app.veggiefood.repository.ChangePasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(val changePasswordRepository: ChangePasswordRepository)
    :ViewModel(){
    val isLoading = MutableLiveData(false)
    val changePasswordLiveData = MutableLiveData<BaseResponse>()

    fun doChangePassword(request: ChangePasswordRequest) {
        viewModelScope.launch {
            isLoading.postValue(true)
            changePasswordRepository.doChangePassword(request).let {
                if (it.body() != null) {
                    changePasswordLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }

}