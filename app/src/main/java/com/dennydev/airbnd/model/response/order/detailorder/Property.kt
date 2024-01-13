package com.dennydev.airbnd.model.response.order.detailorder

import kotlinx.serialization.Serializable

@Serializable
data class Property(
    val area: Area,
    val area_id: Int,
    val category_id: Int,
    val created_at: String,
    val id: Int,
    val image: String,
    val lat: Double,
    val long: Double,
    val name: String,
    val price: Int,
    val slug: String,
    val updated_at: String
)