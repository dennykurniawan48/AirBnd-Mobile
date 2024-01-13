package com.dennydev.airbnd.model.common

object Constant {
    const val BASE_URL = "https://airbnd.000webhostapp.com"
    const val BASE_API = "$BASE_URL/api"
    const val URL_LOGIN = "$BASE_API/login"
    const val URL_REGISTER = "$BASE_API/register"
    const val CATEGORY_URL = "$BASE_API/categories"
    const val PROPERTY_URL = "$BASE_API/properties"
    const val CATEGORY_DETAIL_URL = "$CATEGORY_URL/{slug}"
    const val PROPERTY_DETAIL_URL = "$PROPERTY_URL/{slug}"
    const val CART_SESSION_URL = "$BASE_API/cart-session/{id}"
    const val ORDER_URL = "$BASE_API/order"
    const val DETAIL_ORDER_URL = "$ORDER_URL/{id}"
    const val GOOGLE_LOGIN_URL = "$BASE_API/login-google/{token}"
    const val GOOGLE_CLIENT_ID = "847152624908-381iuq366ujhqq7ubp7h9q1kp23kkfk4.apps.googleusercontent.com"
}