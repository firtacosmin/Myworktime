package com.fcc.myworktime.utils

import android.content.Context
import com.fcc.myworktime.R
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 */
class Messages @Inject constructor(
        c:Context
) {

    val loginErrorMessage:String? = c.getString(R.string.login_error)
}