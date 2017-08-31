package com.fcc.myworktime.ui.projects

import android.util.Log
import com.fcc.myworktime.data.ProjectsDAO
import com.fcc.myworktime.data.UserData
import com.fcc.myworktime.data.database.Projects
import com.fcc.myworktime.utils.Resource
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * The model for [ProjectsPresenter]
 */
class ProjectsModel @Inject constructor(
        val uData:UserData,
        val projDAO:ProjectsDAO
){

    private val TAG: String = "ProjectsModel"

    val projectsObservable:PublishSubject<Resource<List<String>>> = PublishSubject.create()

    var disposable:Disposable = uData.getProjectChangesObservable().subscribe { projectChanged() }


    fun clear(){
        disposable.dispose()
    }

    fun getProjects(): Observable<Resource<List<String>>> {

        if ( uData.dbProjects != null ){
            projectsObservable.onNext(Resource.Companion.success("", createListFromProjects(uData.dbProjects!!)))
        }else{
            projDAO.getProjects(uData.dbUser!!.id).subscribe{projects -> gotProjects(projects)}
        }

        return projectsObservable
    }

    private fun gotProjects(projectsRes: Resource<Projects>) {
        when {
            projectsRes.status == Resource.Status.SUCCESS -> {
                /*save the projects into the userData*/
                uData.dbProjects = projectsRes.data
                /*announce the observers*/
                projectsObservable.onNext(Resource.success(createListFromProjects(projectsRes.data!!)))
            }
            projectsRes.message == ProjectsDAO.PROJECTS_NO_PROJECTS_ERROR -> {
                /*if the empty projects is received then save an empty projects list in the userdata*/
                val projects = Projects()
                projects.userid = uData.dbUser!!.id
                uData.dbProjects = projects
                projectsObservable.onNext(Resource.success(createListFromProjects(uData.dbProjects!!)))
            }
            else -> /*if some other error is received */
                projectsObservable.onNext(Resource.error(projectsRes.message!!, null))
        }

    }




    private fun createListFromProjects(projects:Projects):List<String>{
        return projects.projects
                .map { it.value.name }
    }

    private fun projectChanged() {

        when{
            uData.dbProjects == null || uData.dbProjects?.projects == null || uData.dbProjects?.projects!!.isEmpty() ->{
                projectsObservable.onNext(Resource.error("", null))
            }
            else ->{
                projectsObservable.onNext(Resource.success(createListFromProjects(uData.dbProjects!!)))

            }
        }

    }


}