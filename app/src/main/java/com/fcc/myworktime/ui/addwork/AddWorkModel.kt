package com.fcc.myworktime.ui.addwork

import android.os.Bundle
import com.fcc.myworktime.data.UserData
import com.fcc.myworktime.data.WorkDAO
import com.fcc.myworktime.data.database.Work
import com.fcc.myworktime.utils.Consts
import com.fcc.myworktime.utils.Utils
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
    private var workID = ""
    var work = Work()

    fun getDataFromBundle(data: Bundle) {
        projectID = data.getString(Consts.DETAILS_DISPLAYED_PROJECT_ID,"")
        workID = data.getString(Consts.DETAILS_DISPLAYED_WORK_ID, "")
        if ( workID.isNotEmpty() ){
            work = uData.dbProjects!!.projects[projectID]!!.work[workID]!!
        }

    }

    fun addWork(date:Long, activity:String, hours:Double, description:String ){


        val time = System.currentTimeMillis()
        if ( work.start == 0L ) {
            work.end = time
            work.start = time - Utils.hoursToMillis(hours)
        }else{
            work.end = work.start + Utils.hoursToMillis(hours)
        }
        work.activity = activity
        work.date = date
        work.hours = hours
        work.description = description

        if ( uData.dbProjects!!.projects[projectID]!!.work.isEmpty()  ){
            addFirstWorkToDAO(work)
        }else {
            addWorkToDAO(work)
        }
        uData.dbProjects!!.projects[projectID]!!.work.put(work.start.toString(), work)

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

    fun getWorkActivity():String{
        return work.activity
    }
    fun getWorkDesc():String{
        return work.description
    }
    fun getWorkDate():String{
        return if ( work.start > 0 ) {
            Utils.getDate(work.start)
        }else{
            ""
        }
    }
    fun getWorkHours():String{
        return work.hours.toString()
    }


}