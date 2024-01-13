package com.dennydev.airbnd.repository

import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.form.OrderForm
import com.dennydev.airbnd.model.response.order.createorder.CreateOrder
import com.dennydev.airbnd.model.response.order.detailorder.DetailOrder
import com.dennydev.airbnd.model.response.order.listorder.ListOrder
import com.dennydev.airbnd.model.response.payment.cartsession.CartSession
import com.dennydev.airbnd.network.payment.PaymentApiImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import javax.inject.Inject

@ViewModelScoped
class PaymentRepository @Inject constructor(
    private val client: HttpClient
) {
    suspend fun createOrder(token: String, order: OrderForm): ApiResponse<CreateOrder>{
        return PaymentApiImpl(client).createOrder(token, order)
    }
    suspend fun getSessionCart(token: String, idOrder: String): ApiResponse<CartSession>{
        return PaymentApiImpl(client).getCartSession(token, idOrder)
    }
    suspend fun getDetailOrder(token: String, id: String): ApiResponse<DetailOrder>{
        return PaymentApiImpl(client).getDetailOrder(token, id)
    }
    suspend fun getHistory(token: String): ApiResponse<ListOrder>{
        return PaymentApiImpl(client).getHistory(token)
    }
}