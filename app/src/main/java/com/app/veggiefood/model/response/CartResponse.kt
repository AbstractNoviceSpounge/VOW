package com.app.veggiefood.model.response

data class CartResponse(
    val status: String,
    val total_cost: Double,
    val data: ArrayList<CartModel>
)

data class CartModel(
    val cart_id: Int,
    val qty: String,
    val price: String,
    val variation: CartVariationModel,
    val product: CartProductModel

)

data class CartVariationModel(
    val id: Int,
    val variation: String
)

data class CartProductModel(
    val id: String,
    val url: String,
    val name: String,
    val image: String,
    val base_price: String,
    val discounted_price: String,
    val availability: String,
    val short_description: String

)
