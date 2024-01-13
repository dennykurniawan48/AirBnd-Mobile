package com.dennydev.airbnd.repository

import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.form.LoginForm
import com.dennydev.airbnd.model.form.RegisterForm
import com.dennydev.airbnd.model.response.login.Login
import com.dennydev.airbnd.model.response.register.Register
import com.dennydev.airbnd.network.auth.AuthApiImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import javax.inject.Inject

@ViewModelScoped
class AuthRepository @Inject constructor(
    private val client: HttpClient
) {
    suspend fun login(form: LoginForm): ApiResponse<Login>{
        return AuthApiImpl(client).login(form)
    }

    suspend fun register(form: RegisterForm): ApiResponse<Register>{
        return AuthApiImpl(client).register(form)
    }

    suspend fun loginGoogle(token: String): ApiResponse<Login>{
        return AuthApiImpl(client).loginGoogle(token)
    }
}