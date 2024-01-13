package com.dennydev.airbnd.model.response.order.createorder

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val adult: Int,
    val created_at: String,
    val end: String,
    val id: Int,
    val kids: Int,
    val paid: Boolean,
    val property_id: Int,
    val start: String,
    val total: Int,
    val updated_at: String,
    val user_id: Int
)