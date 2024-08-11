package com.app.veggiefood.model.request

data class DeleteCartRequest(
    val user_id:String,
    val cart_id:String
)