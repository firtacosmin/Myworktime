package com.fcc.myworktime.data

import com.fcc.myworktime.data.database.Project
import com.fcc.myworktime.data.database.Work
import com.fcc.myworktime.di.qualifiers.ProjectsDbRef
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

/**
 * Created by firta on 9/1/2017.
 * the class that will
 */
class WorkDAO @Inject constructor(
        @ProjectsDbRef val projectsDbRef: DatabaseReference
) {
    companion object {
        val DB_OBJECT_NAME = "work"
    }

    fun addWork(work:Work, projectID:String, userID:String){

        projectsDbRef
                .child(userID)
                .child(Project.DB_OBJECT_NAME)
                .child(projectID)
                .child(DB_OBJECT_NAME)
                .child(work.start.toString())
                .setValue(work)

    }

    fun addFirstWork(work:Work, projectID:String, userID:String){
        val workMap = HashMap<String, Work>()
        workMap.put(work.start.toString(), work)
        saveNewWorkMap(workMap,projectID,userID)
    }

    fun saveNewWorkMap(workMap:Map<String,Work>, projectID: String, userID: String){
        projectsDbRef
                .child(userID)
                .child(Project.DB_OBJECT_NAME)
                .child(projectID)
                .child(DB_OBJECT_NAME)
                .setValue(workMap)
    }

}