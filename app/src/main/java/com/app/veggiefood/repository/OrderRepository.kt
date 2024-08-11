package com.app.veggiefood.repository

import com.app.veggiefood.network.ApiService
import javax.inject.Inject

class OrderRepository @Inject constructor(val apiservice: ApiService) {
    suspend fun myOrders(id: String) = apiservice.getOrders(id)
}