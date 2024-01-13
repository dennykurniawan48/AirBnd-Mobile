package com.dennydev.airbnd.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.form.OrderForm
import com.dennydev.airbnd.model.response.order.createorder.CreateOrder
import com.dennydev.airbnd.model.response.property.Property
import com.dennydev.airbnd.repository.PaymentRepository
import com.dennydev.airbnd.repository.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class ReserveViewModel @Inject constructor(
    private val repository: PropertyRepository,
    private val paymentRepository: PaymentRepository
): ViewModel() {
    private val _property = mutableStateOf<ApiResponse<Property>>(ApiResponse.Idle())
    val property: State<ApiResponse<Property>> = _property

    private val _startDate = mutableStateOf<LocalDate?>(null)
    val startDate: State<LocalDate?> = _startDate

    private val _endDate = mutableStateOf<LocalDate?>(null)
    val endDate: State<LocalDate?> = _endDate

    private val _disabledDate = mutableStateOf<List<LocalDate>>(emptyList())
    val disabledDate: State<List<LocalDate>> = _disabledDate

    private val _errorOverlap = mutableStateOf(false)
    val errorOverlap: State<Boolean> = _errorOverlap

    private val _adultGuest = mutableStateOf(1)
    private val _kidGuest = mutableStateOf(0)

    private val _showAlertGuest = mutableStateOf(false)
    val showAlertGuest: State<Boolean> = _showAlertGuest

    val adultGuest: State<Int> = _adultGuest
    val kidGuest: State<Int> =  _kidGuest

    private val _checkoutData = mutableStateOf<ApiResponse<CreateOrder>>(ApiResponse.Idle())
    val checkoutResponse: State<ApiResponse<CreateOrder>> = _checkoutData

    fun getDetailProperty(slug: String){
        _property.value = ApiResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getDetailProperty(slug)
            if(response is ApiResponse.Success){
                val list = mutableListOf<LocalDate>()
                response.data?.data?.booked?.forEach {
                    val date = LocalDate.parse(it)
                    list.add(date)
                }
                _disabledDate.value = list
            }
            _property.value = response
        }
    }

    fun setDate(start: LocalDate, end: LocalDate){
        _errorOverlap.value = false
        _startDate.value = start
        _endDate.value = end
        val daysBetween = ChronoUnit.DAYS.between(start, end).toInt()
        for (i in 0..daysBetween) {
            val currentDate = start.plusDays(i.toLong())
            if(_disabledDate.value.contains(currentDate)){
                _errorOverlap.value = true
            }
        }
    }

    fun increaseAdult(){
        if(_adultGuest.value < 4){
            _adultGuest.value = _adultGuest.value + 1
        }
    }

    fun decreaseAdult(){
        if(_adultGuest.value > 1){
            _adultGuest.value = _adultGuest.value - 1
        }
    }

    fun increaseKid(){
        if(_kidGuest.value < 4){
            _kidGuest.value = _kidGuest.value + 1
        }
    }

    fun decreaseKid(){
        if(_kidGuest.value > 0){
            _kidGuest.value = _kidGuest.value - 1
        }
    }

    fun setShowAlertGuest(show: Boolean){
        _showAlertGuest.value = show
    }

    fun order(token: String, order: OrderForm){
        _checkoutData.value = ApiResponse.Loading()
        viewModelScope.launch {
            _checkoutData.value = paymentRepository.createOrder(token, order)
        }
    }

}