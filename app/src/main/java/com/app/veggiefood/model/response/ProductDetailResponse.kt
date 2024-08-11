package com.app.veggiefood.model.response

data class ProductDetailResponse(
    val status: Boolean,
    val data: ProductDetailModel
)

data class ProductDetailModel(
    val id: String,
    val url: String,
    val trending: String,
    val name: String,
    val base_price: String,
    val discounted_price: String,
    val availability: String,
    val short_description: String,
    val description: String,
    val image: ArrayList<String>
)
