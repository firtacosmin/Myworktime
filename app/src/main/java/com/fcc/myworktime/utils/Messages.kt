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
    val no_projects_message: String? = c.getString(R.string.no_project_message)
    val error_getting_projects: String? = c.getString(R.string.error_getting_projects)
    val error_project_name_empty: String? = c.getString(R.string.error_project_name_empty)
    val tap_again_to_exit: String? = c.getString(R.string.tap_again_to_exit_message)
}