package com.dennydev.airbnd.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.form.LoginForm
import com.dennydev.airbnd.model.response.login.Login
import com.dennydev.airbnd.repository.AuthRepository
import com.dennydev.airbnd.repository.AuthStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val authStoreRepository: AuthStoreRepository,
    val repository: AuthRepository
): ViewModel() {
    private val _isSignedIn = mutableStateOf(false)
    val isSignedIn: State<Boolean> = _isSignedIn

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _isFormValid = mutableStateOf(false)
    val isFormValid = _isFormValid

    private val _isGoogleSignIn = mutableStateOf(false)
    val isGoogleSignIn: State<Boolean> = _isGoogleSignIn

    private val _errorEmail = mutableStateOf("")
    val errorEmail: State<String> = _errorEmail

    fun onChangeIsGoogleLogin(status: Boolean){
        _isGoogleSignIn.value = status
    }

    private val _errorPassword = mutableStateOf("")
    val errorPassword: State<String> = _errorPassword

    private val _showPassword = mutableStateOf(false)
    val showPassword: State<Boolean> = _showPassword

    private val _loginResponse = mutableStateOf<ApiResponse<Login>>(ApiResponse.Idle())
    val loginResponse: State<ApiResponse<Login>> = _loginResponse

    private val emailTouched = mutableStateOf(false)
    private val passwordTouched = mutableStateOf(false)

    fun login(email: String, password: String){
        _loginResponse.value = ApiResponse.Loading()
        viewModelScope.launch {
            val response = repository.login(
                LoginForm(email, password)
            )
            if(response is ApiResponse.Success){
                response.data?.data?.let{
                    authStoreRepository.saveToken(it.token, false)
                    _isSignedIn.value = true
                }
            }
            _loginResponse.value = response
        }
    }

    fun changeShowPassword(){
        _showPassword.value = !_showPassword.value
    }

    fun setEmail(email: String){
        emailTouched.value = true
        _errorEmail.value = ""
        val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        if(!emailRegex.matches(_email.value)) {
            _errorEmail.value = "Email isn't valid."
        }
        if(_email.value.isEmpty()){
            _errorEmail.value = "Can't empty."
        }
        _email.value = email
        checkFormValid()
    }

    fun setPassword(password: String){
        passwordTouched.value = true
        _errorPassword.value = ""
        if(password.length < 6){
            _errorPassword.value = "Min 6 characters"
        }
        _password.value = password
        checkFormValid()
    }

    fun checkFormValid(){
        _isFormValid.value = emailTouched.value && passwordTouched.value && _errorEmail.value.isEmpty() && _errorPassword.value.isEmpty()
    }

    fun loginGoogle(token: String){
        _loginResponse.value = ApiResponse.Loading()
        viewModelScope.launch {
            val response = repository.loginGoogle(token)
            if(response is ApiResponse.Success){
                _loginResponse.value = response
                response.data?.data?.let {
                    authStoreRepository.saveToken(token = it.token, isGoogle = true)
                    _isSignedIn.value = true
                }
            }
            _loginResponse.value = response
        }
    }
}