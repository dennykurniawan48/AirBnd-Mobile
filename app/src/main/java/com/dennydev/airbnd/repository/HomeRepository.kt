package com.dennydev.airbnd.repository

import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.response.categories.Categories
import com.dennydev.airbnd.model.response.categoryproduct.CategoryProduct
import com.dennydev.airbnd.network.home.HomeApiImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import javax.inject.Inject

@ViewModelScoped
class HomeRepository @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getCategories(): ApiResponse<Categories>{
        return HomeApiImpl(client).getCategories()
    }
    suspend fun getProductByCategory(slug: String): ApiResponse<CategoryProduct>{
        val newSlug = if(slug == "/") "room" else slug
        return HomeApiImpl(client).getCategoryProduct(newSlug)
    }
}