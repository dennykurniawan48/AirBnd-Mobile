package com.dennydev.airbnd.network.home

import android.util.Log
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.common.Constant
import com.dennydev.airbnd.model.response.categories.Categories
import com.dennydev.airbnd.model.response.categoryproduct.CategoryProduct
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class HomeApiImpl(
    private val client: HttpClient
): HomeApi {
    override suspend fun getCategories(): ApiResponse<Categories> {
        return try {
            val response: Categories = client.get(Constant.CATEGORY_URL) {
                contentType(ContentType.Application.Json)
            }.body()
            ApiResponse.Success(response)
        }catch (e: Exception){
            Log.d("error categories", e.toString())
            ApiResponse.Error("Something wen't wrong")
        }
    }

    override suspend fun getCategoryProduct(slug: String): ApiResponse<CategoryProduct> {
        return try {
            val response: CategoryProduct = client.get(Constant.CATEGORY_DETAIL_URL.replace("{slug}", slug)) {
                contentType(ContentType.Application.Json)
            }.body()
            ApiResponse.Success(response)
        }catch (e: Exception){
            ApiResponse.Error("Something wen't wrong")
        }
    }
}