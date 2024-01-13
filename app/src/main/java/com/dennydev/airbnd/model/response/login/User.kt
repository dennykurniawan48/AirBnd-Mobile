package com.dennydev.airbnd.model.response.login

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val created_at: String,
    val email: String,
    val email_verified_at: String? = null,
    val id: Int,
    val name: String,
    val updated_at: String
)