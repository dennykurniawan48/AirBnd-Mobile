package com.dennydev.airbnd.model.response.order.listorder

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val created_at: String,
    val id: Int,
    val name: String,
    val updated_at: String
)