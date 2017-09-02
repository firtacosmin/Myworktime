package com.fcc.myworktime.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by firta on 9/1/2017.
 * Object that offers utility methods
 */
object Utils {
    private val DATE_FORMAT = "dd/MM/yyyy hh:mm"
    private val SMALL_DATE_FORMAT = "dd/MM/yyyy"


    fun getDate( milliSeconds:Long):String
    {

        return getDate(milliSeconds, DATE_FORMAT)
    }
    fun getSmallDate( milliSeconds:Long):String{
        return getDate(milliSeconds, SMALL_DATE_FORMAT)
    }

    private fun getDate( milliSeconds:Long,format:String):String{
        // Create a DateFormatter object for displaying date in specified format.
        val formatter: SimpleDateFormat = SimpleDateFormat(format, Locale.US);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar:Calendar = Calendar.getInstance();
        calendar.timeInMillis = milliSeconds;
        return formatter.format(calendar.time);
    }

    fun getMillis(date: String): Long {
        val d = SimpleDateFormat(SMALL_DATE_FORMAT, Locale.ENGLISH).parse(date)
        return d.time
    }


}