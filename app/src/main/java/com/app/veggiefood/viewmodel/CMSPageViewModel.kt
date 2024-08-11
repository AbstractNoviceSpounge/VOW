package com.app.veggiefood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.veggiefood.model.response.CMSPageResponse
import com.app.veggiefood.model.response.CategoryResponse
import com.app.veggiefood.repository.CMSPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CMSPageViewModel @Inject constructor(val cmsPageRepository: CMSPageRepository):ViewModel() {
    val isLoading = MutableLiveData(false)
    val cmsPageLiveData = MutableLiveData<CMSPageResponse>()

    fun getCMSPage(pageName:String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            cmsPageRepository.getCMSPage(pageName).let {
                if (it.body() != null) {
                    cmsPageLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }

}