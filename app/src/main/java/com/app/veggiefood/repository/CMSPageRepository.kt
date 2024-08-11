package com.app.veggiefood.repository

import com.app.veggiefood.network.ApiService
import javax.inject.Inject

class CMSPageRepository @Inject constructor(val apiService: ApiService) {
    suspend fun getCMSPage(pageName: String) = apiService.getCMSPage(pageName)
}