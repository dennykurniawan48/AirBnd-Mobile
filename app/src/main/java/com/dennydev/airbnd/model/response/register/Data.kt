package com.dennydev.airbnd.model.response.register

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val created_at: String,
    val email: String,
    val id: Int,
    val name: String,
    val updated_at: String
)