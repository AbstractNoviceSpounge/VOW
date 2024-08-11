package com.app.veggiefood.model.response

data class OrderResponse(
    val status: Boolean,
    val data: ArrayList<OrderModel>
)

data class OrderModel(
    val id: String,
    val order_id: String,
    val fname: String,
    val lname: String,
    val email: String,
    val phone: String,
    val state: String,
    val address: String,
    val pincode: String,
    val city: String,
    val subtotal: String,
    val final_price: String,
    val shipping_method: String,
    val date: String,
    val products: ArrayList<HistoryModel>
)

data class HistoryModel(
    val name: String,
    val quantity: String,
    val price: String,
    val measure: String,
    val image: String
)