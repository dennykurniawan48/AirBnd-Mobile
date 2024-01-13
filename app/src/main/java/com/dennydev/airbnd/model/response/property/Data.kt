package com.dennydev.airbnd.model.response.property

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val booked: List<String>,
    val `property`: PropertyX
)