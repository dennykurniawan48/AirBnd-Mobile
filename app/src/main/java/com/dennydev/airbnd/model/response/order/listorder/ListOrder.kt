package com.dennydev.airbnd.model.response.order.listorder

import kotlinx.serialization.Serializable

@Serializable
data class ListOrder(
    val `data`: List<Data>
)