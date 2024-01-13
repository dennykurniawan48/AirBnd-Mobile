package com.dennydev.airbnd.model.response.categoryproduct

import kotlinx.serialization.Serializable

@Serializable
data class CategoryProduct(
    val created_at: String,
    val icon: String,
    val id: Int,
    val name: String,
    val properties: List<Property>,
    val updated_at: String,
    val url_slug: String
)