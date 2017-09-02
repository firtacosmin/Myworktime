package com.fcc.myworktime.ui.utils

import android.widget.DatePicker
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import io.reactivex.subjects.PublishSubject
import java.util.*


/**
 * Created by firta on 9/2/2017.
 *
 */
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    val dateSelected = PublishSubject.create<Long>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {

        val c = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, day)
        dateSelected.onNext(c.timeInMillis)

    }
}