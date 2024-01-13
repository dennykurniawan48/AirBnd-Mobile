package com.dennydev.airbnd.model.response.order.createorder

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrder(
    val `data`: Data
)