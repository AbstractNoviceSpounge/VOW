package com.app.veggiefood.model.response

data class SearchResponse(
    val status:String,
    val data:ArrayList<SearchModel>
)
data class SearchModel(
    val id: String,
    val url: String,
    val trending: String,
    val name: String,
    val base_price: String,
    val discounted_price: String,
    val availability: String,
    val short_description: String,
    val description: String,
    val image: String
)