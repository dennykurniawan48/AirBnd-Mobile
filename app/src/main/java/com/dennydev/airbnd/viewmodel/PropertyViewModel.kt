package com.dennydev.airbnd.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.response.property.Property
import com.dennydev.airbnd.repository.AuthStoreRepository
import com.dennydev.airbnd.repository.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(
    private val datastoreRepository: AuthStoreRepository,
    private val repository: PropertyRepository
): ViewModel() {
    private val _property = mutableStateOf<ApiResponse<Property>>(ApiResponse.Idle())
    val property: State<ApiResponse<Property>> = _property

    fun getDetailProperty(slug: String){
        _property.value = ApiResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _property.value = repository.getDetailProperty(slug)
        }
    }
}