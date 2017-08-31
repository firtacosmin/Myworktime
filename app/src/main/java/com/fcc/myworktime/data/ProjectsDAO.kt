package com.fcc.myworktime.data

import com.fcc.myworktime.data.database.Project
import com.fcc.myworktime.data.database.Projects
import com.fcc.myworktime.di.qualifiers.ProjectsDbRef
import com.fcc.myworktime.utils.Resource
import com.google.firebase.database.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * Class used to manage operation on the projects table in the [FirebaseDatabase]
 */
class ProjectsDAO @Inject constructor(@ProjectsDbRef val projectsDbRef: DatabaseReference){

    companion object {
        val PROJECTS_NO_PROJECTS_ERROR = "PROJECTS_NO_PROJECTS_ERROR"
        val PROJECTS_ERROR_GETTING_DATA = "PROJECTS_ERROR_GETTING_DATA"
        val PROJECTS_PROCESS_CANCELED = "PROJECTS_PROCESS_CANCELED"
    }

    private var projectsObservable = PublishSubject.create<Resource<Projects>>()
    private var projectsChildObservable = PublishSubject.create<Resource<Projects>>()


    fun addFirstProject(name:String, userID:String):Projects{
        val entryID = System.currentTimeMillis().toString()
        val projects = Projects()
        projects.projectsID = entryID
        projects.userid = userID
        val proj = Project()
        proj.name = name
        proj.id = entryID
        projects.projects.put(proj.id, proj)

        projectsDbRef.child(entryID).setValue(projects)

        return projects


    }

    fun addNewEmptyProject(projectname:String, projectID:String):Project{
        val newProjID = System.currentTimeMillis()
        val proj = Project()
        proj.name = projectname
        proj.id = newProjID.toString()
        projectsDbRef.child(projectID).child(Project.DB_OBJECT_NAME).child(newProjID.toString()).setValue(proj)

        return proj

    }

    fun listenToProjectsUpdate(userID:String, listener:ChildEventListener){
        projectsDbRef.child("projects").orderByChild("userid").equalTo(userID).addChildEventListener(listener)
    }


    fun getProjects(userID:String):Observable<Resource<Projects>>{

        projectsDbRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val numChildren = dataSnapshot?.childrenCount
                if (numChildren!! <= 0){
                    projectsObservable.onNext(Resource.error(PROJECTS_NO_PROJECTS_ERROR,null))
                    return
                }
                var projects:Projects? = null
                dataSnapshot.children
                        .map {
                            val proj:Projects? = it.getValue(Projects::class.java)
                            proj?.projectsID = it.key
                            proj
                        }
                        .forEach{
                            if ( it?.userid == userID ){
                                projects = it
                            }
                        }
                if ( projects != null ){

                    projectsObservable.onNext(Resource.success(projects!!))
                }else{
                    projectsObservable.onNext(Resource.error(PROJECTS_ERROR_GETTING_DATA, null))
                }
            }

            override fun onCancelled(p0: DatabaseError?) {
                projectsObservable.onNext(Resource.error(PROJECTS_PROCESS_CANCELED, null))
            }

        })

        return projectsObservable
    }



}