package com.app.veggiefood.repository

import com.app.veggiefood.model.request.NotificationRequest
import com.app.veggiefood.network.ApiService
import javax.inject.Inject

class NotificationRepository @Inject constructor(val apiService: ApiService) {

    suspend fun getNotifications(request: NotificationRequest) = apiService.getNotifications(
        request
    )
}