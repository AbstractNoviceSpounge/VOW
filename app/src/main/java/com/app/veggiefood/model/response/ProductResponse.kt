package com.app.veggiefood.model.response

data class ProductResponse(
    val status: Boolean,
    val data:ArrayList<ProductModel>
)

data class ProductModel(
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