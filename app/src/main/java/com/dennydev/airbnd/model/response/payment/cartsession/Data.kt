package com.dennydev.airbnd.model.response.payment.cartsession

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val customer: String,
    val ephemeralKey: String,
    val paymentIntent: String,
    val publishableKey: String
)