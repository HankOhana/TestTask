package com.example.tiprankstask.utils

import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TextUtil {

    fun getFormattedDate(dateText: String, format: String) : String? {
        val formatter = SimpleDateFormat(format, Locale.US)
        try {
            val time = formatter.parse(dateText)?.time
            val now = System.currentTimeMillis()
            val ago = time?.let { DateUtils.getRelativeTimeSpanString(it, now, DateUtils.MINUTE_IN_MILLIS) }
            return ago.toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }
}