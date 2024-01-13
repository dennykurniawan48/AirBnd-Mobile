package com.dennydev.airbnd.network.auth

import android.util.Log
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.common.Constant
import com.dennydev.airbnd.model.form.LoginForm
import com.dennydev.airbnd.model.form.RegisterForm
import com.dennydev.airbnd.model.response.login.Login
import com.dennydev.airbnd.model.response.property.Property
import com.dennydev.airbnd.model.response.register.Register
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class AuthApiImpl(val client: HttpClient): AuthApi {
    override suspend fun login(form: LoginForm): ApiResponse<Login> {
        return try {
            val response = client.post(Constant.URL_LOGIN) {
                contentType(ContentType.Application.Json)
                setBody(form)
            }
            if (response.status.isSuccess()) {
                ApiResponse.Success(response.body())
            } else if (response.status == HttpStatusCode.Unauthorized) {
                ApiResponse.Error("Wrong credentials.")
            } else if (response.status == HttpStatusCode.NotFound) {
                ApiResponse.Error("Email isn't registered.")
            } else if (response.status == HttpStatusCode.UnprocessableEntity) {
                ApiResponse.Error("Validation error.")
            } else {
                ApiResponse.Error("Something wen't wrong.")
            }
        }catch (ex: Exception){
            ApiResponse.Error("Something wen't wrong.")
        }
    }

    override suspend fun register(form: RegisterForm): ApiResponse<Register> {
        return try {
            val response = client.post(Constant.URL_REGISTER) {
                contentType(ContentType.Application.Json)
                setBody(form)
            }
            if (response.status.isSuccess()) {
                ApiResponse.Success(response.body())
            } else if (response.status == HttpStatusCode.UnprocessableEntity) {
                ApiResponse.Error("Validation error.")
            } else if (response.status == HttpStatusCode.Conflict) {
                ApiResponse.Error("Email already registered.")
            } else {
                ApiResponse.Error("Something wen't wrong.")
            }
        }catch (ex: Exception){
            ApiResponse.Error("Something wen't wrong.")
        }
    }

    override suspend fun loginGoogle(token: String): ApiResponse<Login> {
        return try{
            val response: HttpResponse = client.get(Constant.GOOGLE_LOGIN_URL.replace("{token}", token))
            Log.d("response", response.body())
            ApiResponse.Success(response.body())
        }catch (ex: Exception){
            Log.d("error", ex.toString())
            ApiResponse.Error("Something wen't wrong.")
        }
    }
}