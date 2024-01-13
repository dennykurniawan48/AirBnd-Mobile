package com.dennydev.airbnd.model.form

import kotlinx.serialization.Serializable

@Serializable
data class OrderForm(
    val adult: Int,
    val kid: Int,
    val property: String,
    val start: String,
    val end: String
)
