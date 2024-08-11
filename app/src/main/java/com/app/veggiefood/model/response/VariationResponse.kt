package com.app.veggiefood.model.response

data class VariationResponse(
    val status: Boolean,
    val data: ArrayList<VariationModel>
)

data class VariationModel(
    val id: String,
    val variation: String
)