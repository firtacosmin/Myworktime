package com.fcc.myworktime.utils

import android.content.Context

/**
 * Created by firta on 9/2/2017.
 *
 */
class MessagesTest : Messages(null) {

    override val loginErrorMessage:String? = "loginErrorMessage"
    override val no_projects_message: String? = "no_projects_message"
    override val error_getting_projects: String? = "error_getting_projects"
    override val error_project_name_empty: String? = "error_project_name_empty"
    override val tap_again_to_exit: String? = "tap_again_to_exit"
    override val work_state_started: String? = "work_state_started"
    override val work_state_ended: String? = "work_state_ended"
    override val work_btn_end: String? = "work_btn_end"
    override val work_btn_start: String? = "work_btn_start"
    override val remove_project_dialog_tile: String? = "remove_project_dialog_tile"
    override val remove_project_dialog_message: String? = "remove_project_dialog_message"
    override val date_piker_title: String? = "date_piker_title"
    override val field_empty_error: String? = "field_empty_error"
    override val current_work_text: String? = "current_work_text"
    override val hours_small: String? = "hours_small"
    override val work_in_progress: String? = "work_in_progress"
    override val passwords_not_thesame: String? = "passwords_not_thesame"
    override val user_collision_error: String? = "user_collision_error"
    override val weak_pass_error: String? = "weak_pass_error"
    override val invalid_email_error: String? = "invalid_email_error"
    override val error_while_registering: String? = "error_while_registering"
}