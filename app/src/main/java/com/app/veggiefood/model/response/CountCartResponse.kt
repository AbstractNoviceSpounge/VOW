package com.app.veggiefood.model.response

data class CountCartResponse(
    val status: String,
    val data: CountCartModel
)

data class CountCartModel(
    val user_id: String,
    val item_count: String
)
