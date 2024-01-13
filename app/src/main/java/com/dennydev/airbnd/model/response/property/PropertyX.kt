package com.dennydev.airbnd.model.response.property

import kotlinx.serialization.Serializable

@Serializable
data class PropertyX(
    val area: Area,
    val area_id: Int,
    val category: Category,
    val category_id: Int,
    val created_at: String,
    val id: Int,
    val image: String,
    val lat: Double,
    val long: Double,
    val name: String,
    val price: Double,
    val slug: String,
    val updated_at: String
)