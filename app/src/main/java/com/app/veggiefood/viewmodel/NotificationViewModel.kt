package com.app.veggiefood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.veggiefood.model.request.NotificationRequest
import com.app.veggiefood.model.response.BannerResponse
import com.app.veggiefood.model.response.NotificationResponse
import com.app.veggiefood.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(val notificationRepository: NotificationRepository) :
    ViewModel() {
    val isLoading = MutableLiveData(false)
    val isNotificationLiveData = MutableLiveData<NotificationResponse>()

    fun getNotifications(request: NotificationRequest) {
        viewModelScope.launch {
            isLoading.postValue(true)
            notificationRepository.getNotifications(request).let {
                if (it.body() != null) {
                    isNotificationLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }

}