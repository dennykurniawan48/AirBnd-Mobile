package com.dennydev.airbnd.network.home

import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.response.categories.Categories
import com.dennydev.airbnd.model.response.categoryproduct.CategoryProduct

interface HomeApi {
    suspend fun getCategories(): ApiResponse<Categories>
    suspend fun getCategoryProduct(slug: String): ApiResponse<CategoryProduct>
}