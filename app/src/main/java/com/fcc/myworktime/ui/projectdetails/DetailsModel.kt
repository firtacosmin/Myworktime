package com.fcc.myworktime.ui.projectdetails

import android.os.Bundle
import com.fcc.myworktime.data.UserData
import com.fcc.myworktime.data.WorkDAO
import com.fcc.myworktime.data.database.Work
import com.fcc.myworktime.utils.Consts
import com.fcc.myworktime.utils.Messages
import com.fcc.myworktime.utils.Utils
import java.util.*
import javax.inject.Inject

/**
 * Created by firta on 9/1/2017.
 *
 */
class DetailsModel @Inject constructor(
        val uData:UserData,
        val workDAO: WorkDAO,
        val messages: Messages
) {
    var projectID = ""

    var projectName: String = ""
    var projectTotalHours: Double = 0.0


    fun getListOfWork():List<String>{
        return uData.dbProjects!!.projects[projectID]!!.work
                .toSortedMap(compareByDescending<String>{it})
                .map { createString(it.value) }

    }

    fun getDataFromBundle(data: Bundle) {
        projectID = data.getString(Consts.DETAILS_DISPLAYED_PROJECT_ID,"")
        projectName = uData.dbProjects!!.projects[projectID]!!.name
        calculateTotalHours()
    }

    private fun calculateTotalHours() {
        var hours = 0.0
        uData.dbProjects!!.projects[projectID]!!.work.forEach {
            if ( it.value.hours != 0.0 ){
                /*if we have hours then add them*/
                hours+=it.value.hours
            }else{
                /*if we don't have hours then calculate the hours depending on the start and end*/
                if ( it.value.end > 0 && it.value.start < it.value.end){
                    hours += Utils.getHoursFromMillis(it.value.end - it.value.start)
                }
            }
        }

        projectTotalHours = hours
    }



    fun isWorkStarted():Boolean{
        val lastWork = getLastWork()
        return if ( lastWork == null ){
            false
        }else {
            lastWork.end == 0L
        }
    }

    fun endWork(): String {
        val work = getLastWork()
        val time = System.currentTimeMillis()
        work?.end = time
        addWorkToDAO(work!!)
        return createString(work)
    }

    fun startWork(): String {

        val work = Work()
        val time = System.currentTimeMillis()
        work.start = time
        work.end = 0L
        work.activity = WorkDAO.DB_WORK_ACTIVITY_DEFAULT_VALUE
        if ( uData.dbProjects!!.projects[projectID]!!.work.isEmpty()  ){
            addFirstWorkToDAO(work)
        }else {
            addWorkToDAO(work)
        }
        uData.dbProjects!!.projects[projectID]!!.work.put(time.toString(), work)
        return createString(work)

    }

    fun deleteWorkAt(position: Int) {

        val workMap = uData.dbProjects!!.projects[projectID]!!.work
        val idInList = getIdOfPosition(position)
        val removedWork = workMap.remove(idInList)
        addWorkMapToDAO()
        if ( removedWork != null && removedWork.end > 0 && removedWork.start < removedWork.end){
            projectTotalHours -= Utils.getHoursFromMillis(removedWork.end - removedWork.start)
        }

    }

    fun getWorkForPosition(positionInList:Int):Work?{
        val idInList = getIdOfPosition(positionInList)
        return uData.dbProjects!!.projects[projectID]!!.work[idInList]
    }


    private fun getLastWork(): Work? {
         val map = uData.dbProjects!!.projects[projectID]!!.work
                .toSortedMap()

        if ( map.isEmpty() ){
            return null
        }else {
            val lastKey = map.lastKey()
            return uData.dbProjects!!.projects[projectID]!!.work[lastKey]
        }
    }
    fun getIdOfPosition(position: Int): String {

        var id = ""
        val map = uData.dbProjects!!.projects[projectID]!!.work
                .toSortedMap(compareByDescending<String>{it})
        map.keys.forEachIndexed { index, s ->
            if ( index == position ){
                id = s
            }
        }

        return id

    }
    private fun createString(work:Work):String{
        if ( work.end == 0L ){
            /*if work in progress*/
            return messages.work_in_progress + work.activity
        }else {
            var hours = work.hours
            if (hours == 0.0) {
                hours = Utils.getHoursFromMillis(work.end - work.start)
            }
            return "Worked on ${work.activity} for $hours hours"
        }
//        return "${Utils.getDate(work.start)}\n-\n${Utils.getDate(work.end)}"
    }

    private fun addWorkToDAO(work:Work){
        workDAO.addWork(work,projectID,uData.dbUser!!.id )
    }

    private fun addFirstWorkToDAO(work: Work) {
        workDAO.addFirstWork(work,projectID,uData.dbUser!!.id )
    }

    private fun addWorkMapToDAO(){
        workDAO.saveNewWorkMap(uData.dbProjects!!.projects[projectID]!!.work, projectID, uData.dbUser!!.id)
    }



}
