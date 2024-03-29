package com.dennydev.airbnd.component

import java.text.SimpleDateFormat
import java.util.Locale

fun IsoToDateString(iso8601: String): String {
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = dateFormat.parse(iso8601)

        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val formattedDate = outputFormat.format(date)

        return formattedDate
    }catch(e: Exception){
        return "N/A"
    }

}