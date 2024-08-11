package com.app.veggiefood.repository

import com.app.veggiefood.model.request.ChangePasswordRequest
import com.app.veggiefood.network.ApiService
import javax.inject.Inject

class ChangePasswordRepository @Inject constructor(val apiService: ApiService) {
    suspend fun doChangePassword(request: ChangePasswordRequest) = apiService.doChangePassword(
        request
    )
}