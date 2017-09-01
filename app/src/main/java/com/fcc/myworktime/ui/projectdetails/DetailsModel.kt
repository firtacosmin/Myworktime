package com.fcc.myworktime.ui.projectdetails

import android.os.Bundle
import com.fcc.myworktime.data.UserData
import com.fcc.myworktime.utils.Consts
import javax.inject.Inject

/**
 * Created by firta on 9/1/2017.
 *
 */
class DetailsModel @Inject constructor(
        val uData:UserData
) {
    private var projectID = ""


    fun getListOfWork():List<String>{
        return uData.dbProjects!!.projects[projectID]!!.work
                .map { "${it.value.start} - ${it.value.end}" }
    }

    fun getDataFromBundle(data: Bundle) {
        projectID = data.getString(Consts.DETAILS_DISPLAYED_PROJECT_ID,"")
    }


}