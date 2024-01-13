package com.dennydev.airbnd.model.response.categories

import kotlinx.serialization.Serializable

@Serializable
data class Categories(
    val `data`: List<Data>
)