package com.dennydev.airbnd.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennydev.airbnd.repository.AuthStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val datastoreRepository: AuthStoreRepository,
): ViewModel() {
    val token = datastoreRepository.flowToken
    private val _isSignedIn = mutableStateOf(false)
    val isSignedIn: State<Boolean> = _isSignedIn
    val isGoogle = datastoreRepository.flowLoginGoogle

    init {
        viewModelScope.launch {
            datastoreRepository.flowToken.collect { token ->
                _isSignedIn.value = token.isNotEmpty()
                Log.d("isSigned", _isSignedIn.value.toString())
            }
        }
    }

    fun getSignedStatus(): Boolean{
        var signed = false
        viewModelScope.launch {
            token.collectLatest {
                signed = it.isNotEmpty()
            }
        }
        return signed
    }

    fun logout(){
        viewModelScope.launch {
            datastoreRepository.removeToken()
            _isSignedIn.value = false
        }
    }
}