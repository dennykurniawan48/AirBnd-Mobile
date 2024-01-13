package com.dennydev.airbnd.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.form.RegisterForm
import com.dennydev.airbnd.model.response.register.Register
import com.dennydev.airbnd.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){
    private val _registerResponse = mutableStateOf<ApiResponse<Register>>(ApiResponse.Idle())
    val registerResponse: State<ApiResponse<Register>> = _registerResponse

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _errorName = mutableStateOf("")
    val errorName: State<String> = _errorName

    private val _errorEmail = mutableStateOf("")
    val errorEmail: State<String> = _errorEmail

    private val _errorPassword = mutableStateOf("")
    val errorPassword: State<String> = _errorPassword

    private val _validForm = mutableStateOf(false)
    val validForm: State<Boolean> = _validForm

    private val _touchedName = mutableStateOf(false)
    val touchedName: State<Boolean> = _touchedName

    private val _showPassword = mutableStateOf(false)
    val showPassword: State<Boolean> = _showPassword

    private val nameTouched = mutableStateOf(false)
    private val emailTouched = mutableStateOf(false)
    private val passwordTouched = mutableStateOf(false)

    fun changeShowPassword(){
        _showPassword.value = !showPassword.value
    }

    fun setName(name: String){
        nameTouched.value = true
        _errorName.value = ""
        if(name.trim().length < 3){
            _errorName.value = "Min 3 characters."
        }
        _name.value = name
        checkFormValid()
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
        _validForm.value = nameTouched.value &&
                passwordTouched.value &&
                emailTouched.value &&
                _errorName.value.isEmpty() &&
                _errorEmail.value.isEmpty() &&
                _errorPassword.value.isEmpty()
    }

    fun register(name: String, email: String, password: String){
        _registerResponse.value = ApiResponse.Loading()
        viewModelScope.launch {
            _registerResponse.value = repository.register(
                RegisterForm(
                    name, email, password
                )
            )
        }
    }
}