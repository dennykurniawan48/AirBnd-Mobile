package com.dennydev.airbnd.network.auth

import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.form.LoginForm
import com.dennydev.airbnd.model.form.RegisterForm
import com.dennydev.airbnd.model.response.login.Login
import com.dennydev.airbnd.model.response.register.Register

interface AuthApi {
    suspend fun login(form: LoginForm): ApiResponse<Login>
    suspend fun register(form: RegisterForm): ApiResponse<Register>
    suspend fun loginGoogle(token: String): ApiResponse<Login>
}