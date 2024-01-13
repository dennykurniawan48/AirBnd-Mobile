package com.dennydev.airbnd.network.payment

import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.form.OrderForm
import com.dennydev.airbnd.model.response.order.createorder.CreateOrder
import com.dennydev.airbnd.model.response.order.detailorder.DetailOrder
import com.dennydev.airbnd.model.response.order.listorder.ListOrder
import com.dennydev.airbnd.model.response.payment.cartsession.CartSession

interface PaymentApi {
    suspend fun getDetailOrder(token: String, id: String): ApiResponse<DetailOrder>
    suspend fun createOrder(token: String, order: OrderForm): ApiResponse<CreateOrder>
    suspend fun getCartSession(token: String, idOrder: String): ApiResponse<CartSession>
    suspend fun getHistory(token: String): ApiResponse<ListOrder>
}