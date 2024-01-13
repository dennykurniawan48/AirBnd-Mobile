package com.dennydev.airbnd.network.property

import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.response.property.Property

interface PropertyApi {
    suspend fun getDetailProperty(slug: String): ApiResponse<Property>
}