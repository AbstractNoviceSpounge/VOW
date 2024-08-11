package com.app.veggiefood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.veggiefood.model.response.BannerResponse
import com.app.veggiefood.model.response.CategoryResponse
import com.app.veggiefood.repository.BannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(val bannerRepository: BannerRepository):ViewModel() {
    val isLoading = MutableLiveData(false)
    val isBannerLiveData = MutableLiveData<BannerResponse>()

    fun getBanner() {
        viewModelScope.launch {
            isLoading.postValue(true)
            bannerRepository.myBanner().let {
                if (it.body() != null) {
                    isBannerLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }

}