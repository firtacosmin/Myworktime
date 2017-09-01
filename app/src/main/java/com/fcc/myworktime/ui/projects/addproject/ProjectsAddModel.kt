package com.fcc.myworktime.ui.projects.addproject

import com.fcc.myworktime.data.ProjectsDAO
import com.fcc.myworktime.data.UserData
import javax.inject.Inject

/**
 * Created by firta on 9/1/2017.
 *
 */
class ProjectsAddModel @Inject constructor(
        val projectsDAO: ProjectsDAO,
        val uData:UserData
) {

    fun addProject(name:String){
        if ( uData.dbProjects != null && uData.dbProjects?.projects!!.isNotEmpty()) {
            /*if some projects are already in the db then add an other one*/
            val proj = projectsDAO.addNewEmptyProject(name, uData.dbProjects!!.projectsID)
            uData.addNewProject(proj)
        }else{
            /*if the db is empty then add the first one*/
            val projects = projectsDAO.addFirstProject(name, uData.dbUser!!.id)
            uData.dbProjects = projects
        }
    }

}