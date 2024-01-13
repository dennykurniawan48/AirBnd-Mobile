package com.dennydev.airbnd.model.response.payment.cartsession

import kotlinx.serialization.Serializable

@Serializable
data class CartSession(
    val `data`: Data
)