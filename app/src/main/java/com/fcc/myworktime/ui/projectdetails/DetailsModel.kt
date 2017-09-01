package com.fcc.myworktime.ui.projectdetails

import android.os.Bundle
import com.fcc.myworktime.data.UserData
import com.fcc.myworktime.data.WorkDAO
import com.fcc.myworktime.data.database.Work
import com.fcc.myworktime.utils.Consts
import com.fcc.myworktime.utils.Utils
import javax.inject.Inject

/**
 * Created by firta on 9/1/2017.
 *
 */
class DetailsModel @Inject constructor(
        val uData:UserData,
        val workDAO: WorkDAO
) {
    private var projectID = ""


    fun getListOfWork():List<String>{
        return uData.dbProjects!!.projects[projectID]!!.work
                .toSortedMap(compareByDescending<String>{it})
                .map { createString(it.value) }

    }

    fun getDataFromBundle(data: Bundle) {
        projectID = data.getString(Consts.DETAILS_DISPLAYED_PROJECT_ID,"")
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
        workMap.remove(idInList)
        addWorkMapToDAO()

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
    private fun getIdOfPosition(position: Int): String {

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
        return "${Utils.getDate(work.start)}\n-\n${Utils.getDate(work.end)}"
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
