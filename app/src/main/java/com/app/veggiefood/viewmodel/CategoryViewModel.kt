package com.app.veggiefood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.veggiefood.model.request.LoginRequest
import com.app.veggiefood.model.response.CategoryResponse
import com.app.veggiefood.model.response.LoginResponse
import com.app.veggiefood.repository.CategoryRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(val categoryRespository: CategoryRespository) :
    ViewModel() {

    val isLoading = MutableLiveData(false)
    val categoryLiveData = MutableLiveData<CategoryResponse>()

    fun getCategory() {
        viewModelScope.launch {
            isLoading.postValue(true)
            categoryRespository.getCategory().let {
                if (it.body() != null) {
                    categoryLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }

}