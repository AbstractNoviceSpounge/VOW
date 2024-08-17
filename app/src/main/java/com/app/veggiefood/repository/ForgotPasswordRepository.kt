package com.app.veggiefood.repository

import com.app.veggiefood.model.request.ForgotPasswordRequest
import com.app.veggiefood.network.ApiService
import javax.inject.Inject

class ForgotPasswordRepository @Inject constructor(val apiService: ApiService) {
    suspend fun doForgotPasword(request: ForgotPasswordRequest) =
        apiService.doForgotPassword(request)
}