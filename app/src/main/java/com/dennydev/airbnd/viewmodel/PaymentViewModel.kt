package com.dennydev.airbnd.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.response.order.detailorder.DetailOrder
import com.dennydev.airbnd.model.response.order.listorder.ListOrder
import com.dennydev.airbnd.model.response.payment.cartsession.CartSession
import com.dennydev.airbnd.repository.PaymentRepository
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val repository: PaymentRepository
): ViewModel() {
    private val _order = mutableStateOf<ApiResponse<DetailOrder>>(ApiResponse.Idle())
    val order: State<ApiResponse<DetailOrder>> = _order

    private val _listOrder = mutableStateOf<ApiResponse<ListOrder>>(ApiResponse.Idle())
    val listOrder: State<ApiResponse<ListOrder>> = _listOrder

    val cartSession = mutableStateOf<ApiResponse<CartSession>>(ApiResponse.Idle())
    private val _checkoutState = MutableStateFlow<PaymentSheetResult?>(null)
    val checkoutState: StateFlow<PaymentSheetResult?> = _checkoutState

    fun getOrder(token: String, idOrder: String){
        _order.value = ApiResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _order.value = repository.getDetailOrder(token, idOrder)
        }
    }

    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        _checkoutState.update { paymentSheetResult }
    }

    fun getPaymentSheetData(token: String, orderId: String){
        cartSession.value = ApiResponse.Loading()
        viewModelScope.launch {
            cartSession.value = repository.getSessionCart(token, orderId)
        }
    }

    fun getHistory(token: String){
        _listOrder.value = ApiResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _listOrder.value = repository.getHistory(token)
        }
    }
}