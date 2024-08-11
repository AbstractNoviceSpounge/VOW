package com.app.veggiefood.repository

import com.app.veggiefood.network.ApiService
import javax.inject.Inject

class CategoryRespository @Inject constructor(val apiService: ApiService) {
    suspend fun getCategory() = apiService.getCategory()
}