package com.app.veggiefood.repository

import com.app.veggiefood.model.request.DeleteUserRequest
import com.app.veggiefood.model.request.ProfileRequest
import com.app.veggiefood.network.ApiService
import javax.inject.Inject

class ProfileRepository @Inject constructor(val apiService: ApiService) {
    suspend fun getProfile(
        id: String
    ) = apiService.getProfile(id)

    suspend fun updateProfile(id: String, request: ProfileRequest) =
        apiService.updateProfile(id, request)

    suspend fun deleteUser(request: DeleteUserRequest)=apiService.deleteUser(
        request
    )
}