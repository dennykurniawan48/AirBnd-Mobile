package com.dennydev.airbnd.network.payment

import android.util.Log
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.common.Constant
import com.dennydev.airbnd.model.form.OrderForm
import com.dennydev.airbnd.model.response.order.createorder.CreateOrder
import com.dennydev.airbnd.model.response.order.detailorder.DetailOrder
import com.dennydev.airbnd.model.response.order.listorder.ListOrder
import com.dennydev.airbnd.model.response.payment.cartsession.CartSession
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class PaymentApiImpl(val client: HttpClient): PaymentApi {
    override suspend fun getDetailOrder(token: String, id: String): ApiResponse<DetailOrder> {
        return try{
            val response: DetailOrder = client.get(Constant.DETAIL_ORDER_URL.replace("{id}", id)) {
                headers{
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }.body()
            ApiResponse.Success(response)
        }catch (e: Exception){
            ApiResponse.Error("Something wen't wrong")
        }
    }

    override suspend fun createOrder(token: String, order: OrderForm): ApiResponse<CreateOrder> {
        return try{
            val response: HttpResponse = client.post(Constant.ORDER_URL){
                contentType(ContentType.Application.Json)
                setBody(order)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }
            ApiResponse.Success(response.body())
        }catch(e: Exception){
            Log.d("error order", e.toString())
            ApiResponse.Error("Something wen't wrong")
        }
    }

    override suspend fun getCartSession(token: String, idOrder: String): ApiResponse<CartSession> {
        return try {
            val response: CartSession = client.get(Constant.CART_SESSION_URL.replace("{id}", idOrder)) {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }.body()
            ApiResponse.Success(response)
        }catch(e: Exception){
            ApiResponse.Error("Something wen't wrong")
        }
    }

    override suspend fun getHistory(token: String): ApiResponse<ListOrder> {
        return try {
            val response: ListOrder = client.get(Constant.ORDER_URL) {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }.body()
            ApiResponse.Success(response)
        }catch(e: Exception){
            ApiResponse.Error("Something wen't wrong")
        }
    }
}