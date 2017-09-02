package com.fcc.myworktime.ui.addwork

import android.os.Bundle
import com.fcc.myworktime.data.UserData
import com.fcc.myworktime.data.WorkDAO
import com.fcc.myworktime.data.database.Work
import com.fcc.myworktime.utils.Consts
import javax.inject.Inject

/**
 * Created by firta on 9/2/2017.
 *
 */
class AddWorkModel @Inject constructor(
        val uData: UserData,
        val workDAO: WorkDAO
) {

    private var projectID = ""

    fun getDataFromBundle(data: Bundle) {
        projectID = data.getString(Consts.DETAILS_DISPLAYED_PROJECT_ID,"")

    }

    fun addWork(date:Long, activity:String, hours:Double, description:String ){

        val work = Work()
        val time = System.currentTimeMillis()
        work.start = time
        work.activity = activity
        work.date = date
        work.hours = hours
        work.description = description

        if ( uData.dbProjects!!.projects[projectID]!!.work.isEmpty()  ){
            addFirstWorkToDAO(work)
        }else {
            addWorkToDAO(work)
        }
        uData.dbProjects!!.projects[projectID]!!.work.put(time.toString(), work)

    }

    private fun addWorkToDAO(work:Work){
        workDAO.addWork(work,projectID,uData.dbUser!!.id )
    }

    private fun addFirstWorkToDAO(work: Work) {
        workDAO.addFirstWork(work,projectID,uData.dbUser!!.id )
    }

    fun getProjectName(): String {
        return uData.dbProjects!!.projects[projectID]!!.name
    }


}