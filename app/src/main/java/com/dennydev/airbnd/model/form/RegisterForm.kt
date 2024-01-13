package com.dennydev.airbnd.model.form

import kotlinx.serialization.Serializable

@Serializable
data class RegisterForm(
    val name: String,
    val email: String,
    val password: String
)