package com.app.veggiefood.repository

import com.app.veggiefood.network.ApiService
import javax.inject.Inject

class BannerRepository @Inject constructor(val apiService: ApiService) {
    suspend fun myBanner() = apiService.myBanner()
}