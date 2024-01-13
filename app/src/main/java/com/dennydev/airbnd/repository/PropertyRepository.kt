package com.dennydev.airbnd.repository

import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.response.property.Property
import com.dennydev.airbnd.network.property.PropertyApiImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import javax.inject.Inject

@ViewModelScoped
class PropertyRepository @Inject constructor(
    val client: HttpClient
) {
    suspend fun getDetailProperty(slug:String): ApiResponse<Property>{
        return PropertyApiImpl(client).getDetailProperty(slug)
    }
}