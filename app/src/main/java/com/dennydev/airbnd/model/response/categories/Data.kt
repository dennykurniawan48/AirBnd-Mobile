package com.dennydev.airbnd.model.response.categories

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val created_at: String,
    val icon: String,
    val id: Int,
    val name: String,
    val updated_at: String,
    val url_slug: String
)