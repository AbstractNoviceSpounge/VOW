package com.app.veggiefood.repository

import com.app.veggiefood.model.request.SignupRequest
import com.app.veggiefood.network.ApiService
import javax.inject.Inject

class SignupRepository @Inject constructor(val apiService: ApiService) {
    suspend fun doRegister(request: SignupRequest) =apiService.doRegister(request)
}