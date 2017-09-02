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
    val work_state_started: String? = c.getString(R.string.work_state_started)
    val work_state_ended: String? = c.getString(R.string.work_state_ended)
    val work_btn_end: String? = c.getString(R.string.work_button_text_end)
    val work_btn_start: String? = c.getString(R.string.work_button_text_start)
    val remove_project_dialog_tile: String? = c.getString(R.string.remove_project_dialog_title)
    val remove_project_dialog_message: String? = c.getString(R.string.remove_project_dialog_message)
    val date_piker_title: String? = c.getString(R.string.date_piker_title)
    val field_empty_error: String? = c.getString(R.string.field_empty_error)
    val current_work_text: String? = c.getString(R.string.current_work_text)
    val hours_small: String? = c.getString(R.string.hours_small)
    val work_in_progress: String? = c.getString(R.string.work_in_progress)
    val passwords_not_thesame: String? = c.getString(R.string.passwords_not_the_same)
    val user_collision_error: String? = c.getString(R.string.user_collision_error)
    val weak_pass_error: String? = c.getString(R.string.weak_pass_error)
    val invalid_email_error: String? = c.getString(R.string.invalid_email_error)
    val error_while_registering: String? = c.getString(R.string.error_while_registering)

}