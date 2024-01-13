package com.dennydev.airbnd.model.response.order.listorder

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val adult: Int,
    val created_at: String,
    val end: String,
    val id: Int,
    val kids: Int,
    val paid: Int,
    val `property`: Property,
    val property_id: Int,
    val start: String,
    val total: Double,
    val updated_at: String,
    val user_id: Int
)