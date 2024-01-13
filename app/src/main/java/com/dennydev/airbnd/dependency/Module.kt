package com.dennydev.airbnd.dependency

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@dagger.Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    fun provideClient(): HttpClient {
        return HttpClient(Android) {
//            install(HttpCookies) {
//                // Configure cookie handling options here
//                storage = AcceptAllCookiesStorage() // Example: accept all cookies
//            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
//            install(Logging){
//                logger=object: Logger {
//                    override fun log(message: String) {
//                        Log.d("HTTP Client", message)
//                    }
//                }
//                level=LogLevel.ALL
//            }
        }
    }
}