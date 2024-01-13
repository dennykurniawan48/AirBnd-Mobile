package com.dennydev.airbnd.network.property

import android.util.Log
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.common.Constant
import com.dennydev.airbnd.model.response.property.Property
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PropertyApiImpl(val client: HttpClient): PropertyApi {
    override suspend fun getDetailProperty(slug: String): ApiResponse<Property> {
        return try{
            val response: Property = client.get(Constant.PROPERTY_DETAIL_URL.replace("{slug}", slug)).body()
            ApiResponse.Success(response)
        }catch (ex: Exception){
            Log.d("error property", Constant.PROPERTY_URL.replace("{slug}", slug))
            ApiResponse.Error("Something wen't wrong.")
        }
    }
}