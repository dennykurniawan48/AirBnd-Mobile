package com.dennydev.airbnd.model.response.login

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val token: String,
    val user: User
)