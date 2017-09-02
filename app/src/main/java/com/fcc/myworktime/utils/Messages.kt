package com.fcc.myworktime.utils

import android.content.Context
import com.fcc.myworktime.R
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 */
open class Messages @Inject constructor(
        c:Context?
) {

    open val loginErrorMessage:String? = c?.getString(R.string.login_error)
    open val no_projects_message: String? = c?.getString(R.string.no_project_message)
    open val error_getting_projects: String? = c?.getString(R.string.error_getting_projects)
    open val error_project_name_empty: String? = c?.getString(R.string.error_project_name_empty)
    open val tap_again_to_exit: String? = c?.getString(R.string.tap_again_to_exit_message)
    open val work_state_started: String? = c?.getString(R.string.work_state_started)
    open val work_state_ended: String? = c?.getString(R.string.work_state_ended)
    open val work_btn_end: String? = c?.getString(R.string.work_button_text_end)
    open val work_btn_start: String? = c?.getString(R.string.work_button_text_start)
    open val remove_project_dialog_tile: String? = c?.getString(R.string.remove_project_dialog_title)
    open val remove_project_dialog_message: String? = c?.getString(R.string.remove_project_dialog_message)
    open val date_piker_title: String? = c?.getString(R.string.date_piker_title)
    open val field_empty_error: String? = c?.getString(R.string.field_empty_error)
    open val current_work_text: String? = c?.getString(R.string.current_work_text)
    open val hours_small: String? = c?.getString(R.string.hours_small)
    open val work_in_progress: String? = c?.getString(R.string.work_in_progress)
    open val passwords_not_thesame: String? = c?.getString(R.string.passwords_not_the_same)
    open val user_collision_error: String? = c?.getString(R.string.user_collision_error)
    open val weak_pass_error: String? = c?.getString(R.string.weak_pass_error)
    open val invalid_email_error: String? = c?.getString(R.string.invalid_email_error)
    open val error_while_registering: String? = c?.getString(R.string.error_while_registering)

}