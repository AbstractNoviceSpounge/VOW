package com.app.veggiefood.repository

import com.app.veggiefood.model.request.AddEditCartRequest
import com.app.veggiefood.model.request.CalculatePriceRequest
import com.app.veggiefood.model.request.CartRequest
import com.app.veggiefood.model.request.DeleteCartRequest
import com.app.veggiefood.model.request.PlaceOrderRequest
import com.app.veggiefood.network.ApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(val apiService: ApiService) {
    suspend fun getProducts(id: String) = apiService.getProducts(id)

    suspend fun searchProducts(query: String) = apiService.getSearchProduct(query)

    suspend fun getProductDetail(id: String) = apiService.getProductDetail(id)

    suspend fun getVariation() = apiService.getVariation()

    suspend fun getCalculatePrice(request: CalculatePriceRequest) =
        apiService.getCalculatePrice(request)

    suspend fun addEditCartRequest(request: AddEditCartRequest) =
        apiService.addEditCartRequest(request)

    suspend fun myCart(request: CartRequest) = apiService.getMyCart(request)

    suspend fun deleteCart(request: DeleteCartRequest) = apiService.deleteCart(request)

    suspend fun placeOrder(request: PlaceOrderRequest) = apiService.placeOrder(request)

    suspend fun totalCount(request: CartRequest) = apiService.getTotalCount(request)
}