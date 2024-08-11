package com.app.veggiefood.repository

import com.app.veggiefood.model.request.LoginRequest
import com.app.veggiefood.network.ApiService
import javax.inject.Inject

class LoginRepository @Inject constructor(val apiService: ApiService) {

    suspend fun doLogin(request: LoginRequest) = apiService.doLogin(request)
}