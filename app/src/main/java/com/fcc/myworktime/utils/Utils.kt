package com.fcc.myworktime.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by firta on 9/1/2017.
 * Object that offers utility methods
 */
object Utils {
    private val DATE_FORMAT = "dd/MM/yyyy hh:mm"

    fun getDate( milliSeconds:Long):String
    {
        // Create a DateFormatter object for displaying date in specified format.
         val formatter: SimpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar:Calendar = Calendar.getInstance();
        calendar.timeInMillis = milliSeconds;
        return formatter.format(calendar.time);
    }

}