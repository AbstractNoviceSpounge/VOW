package com.app.veggiefood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.veggiefood.model.request.AddEditCartRequest
import com.app.veggiefood.model.request.CalculatePriceRequest
import com.app.veggiefood.model.request.CartRequest
import com.app.veggiefood.model.request.DeleteCartRequest
import com.app.veggiefood.model.request.PlaceOrderRequest
import com.app.veggiefood.model.response.BaseResponse
import com.app.veggiefood.model.response.CalculatePriceResponse
import com.app.veggiefood.model.response.CartResponse
import com.app.veggiefood.model.response.CategoryResponse
import com.app.veggiefood.model.response.CountCartResponse
import com.app.veggiefood.model.response.DeleteCartResponse
import com.app.veggiefood.model.response.PlaceOrderResponse
import com.app.veggiefood.model.response.ProductDetailResponse
import com.app.veggiefood.model.response.ProductResponse
import com.app.veggiefood.model.response.SearchResponse
import com.app.veggiefood.model.response.VariationResponse
import com.app.veggiefood.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(val productRepository: ProductRepository) : ViewModel() {
    val isLoading = MutableLiveData(false)
    val productLiveData = MutableLiveData<ProductResponse>()
    val productDetailLiveData = MutableLiveData<ProductDetailResponse>()
    val variationLiveData = MutableLiveData<VariationResponse>()
    val calculatePriceLiveData = MutableLiveData<CalculatePriceResponse>()
    val addEditCartLiveData = MutableLiveData<DeleteCartResponse>()
    val myCartLiveData = MutableLiveData<CartResponse>()
    val deleteCartLiveData = MutableLiveData<DeleteCartResponse>()
    val placeOrderLiveData = MutableLiveData<PlaceOrderResponse>()
    val totalCountLiveData = MutableLiveData<CountCartResponse>()
    val searchProductLiveData = MutableLiveData<SearchResponse>()

    fun getProduct(id: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            productRepository.getProducts(id).let {
                if (it.body() != null) {
                    productLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }

    fun searchProduct(query: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            productRepository.searchProducts(query).let {
                if (it.body() != null) {
                    searchProductLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }


    fun getVariation() {
        viewModelScope.launch {
            isLoading.postValue(true)
            productRepository.getVariation().let {
                if (it.body() != null) {
                    variationLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }


    fun getCalculatePrice(request: CalculatePriceRequest) {
        viewModelScope.launch {
            isLoading.postValue(true)
            productRepository.getCalculatePrice(request).let {
                if (it.body() != null) {
                    calculatePriceLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }

    fun addEditCartApi(request: AddEditCartRequest) {
        viewModelScope.launch {
            isLoading.postValue(true)
            productRepository.addEditCartRequest(request).let {
                if (it.body() != null) {
                    addEditCartLiveData.postValue(it.body() as DeleteCartResponse)
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }


    fun getProductDetail(id: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            productRepository.getProductDetail(id).let {
                if (it.body() != null) {
                    productDetailLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }

    fun getCart(request: CartRequest) {
        viewModelScope.launch {
            isLoading.postValue(true)
            productRepository.myCart(request).let {
                if (it.body() != null) {
                    myCartLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }

    fun deleteCart(request: DeleteCartRequest) {
        viewModelScope.launch {
            isLoading.postValue(true)
            productRepository.deleteCart(request).let {
                if (it.body() != null) {
                    deleteCartLiveData.postValue(it.body() as DeleteCartResponse)
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }


    fun placeOrder(request: PlaceOrderRequest) {
        viewModelScope.launch {
            isLoading.postValue(true)
            productRepository.placeOrder(request).let {
                if (it.body() != null) {
                    placeOrderLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }


    fun totalCount(request: CartRequest) {
        viewModelScope.launch {
            isLoading.postValue(true)
            productRepository.totalCount(request).let {
                if (it.body() != null) {
                    totalCountLiveData.postValue(it.body())
                    isLoading.postValue(false)
                } else {
                    isLoading.postValue(false)
                }
            }

        }
    }

}