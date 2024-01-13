package com.dennydev.airbnd.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.response.categories.Categories
import com.dennydev.airbnd.model.response.categoryproduct.CategoryProduct
import com.dennydev.airbnd.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): ViewModel() {
    private val _categories = mutableStateOf<ApiResponse<Categories>>(ApiResponse.Idle())
    val categories: State<ApiResponse<Categories>> = _categories
    private val _categoryProduct = mutableStateOf<ApiResponse<CategoryProduct>>(ApiResponse.Idle())
    val categoryProduct: State<ApiResponse<CategoryProduct>> = _categoryProduct

    private val _showAlertLogout = mutableStateOf(false)
    val showAlertLogout: State<Boolean> = _showAlertLogout

    private val _selectedCategory = mutableStateOf("/")
    val selectedCategory: State<String> = _selectedCategory

    private val _refresh = mutableStateOf(false)
    val refresh: State<Boolean> = _refresh

    fun changeSelectedCategory(slug: String){
        _selectedCategory.value = slug
    }

    fun getAllCategory(){
        _categories.value = ApiResponse.Loading()
        viewModelScope.launch {
            _categories.value = repository.getCategories()
        }
    }

    fun getProductCategory(slug: String){
        _categoryProduct.value = ApiResponse.Loading()
        viewModelScope.launch {
            _categoryProduct.value = repository.getProductByCategory(slug)
        }
    }

    fun changeRefresh(isRefresh: Boolean){
        _refresh.value = isRefresh
    }

    fun changeShowAlertLogout(show: Boolean){
        _showAlertLogout.value = show
    }
}