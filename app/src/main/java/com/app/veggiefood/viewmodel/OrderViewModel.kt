package com.app.veggiefood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.veggiefood.model.response.BannerResponse
import com.app.veggiefood.model.response.OrderResponse
import com.app.veggiefood.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(val orderRepository: OrderRepository):ViewModel() {
    val isLoading = MutableLiveData(false)
    val isOrderLiveData = MutableLiveData<OrderResponse>()

    fun getOrders(id:String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            orderRepository.myOrders(id).let {
                if (it.body() != null) {
                    isOrderLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }



}