package com.dennydev.airbnd.model.response.order.detailorder

import kotlinx.serialization.Serializable

@Serializable
data class Area(
    val country: Country,
    val country_id: Int,
    val created_at: String,
    val id: Int,
    val name: String,
    val updated_at: String
)