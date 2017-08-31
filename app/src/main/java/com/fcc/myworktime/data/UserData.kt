package com.fcc.myworktime.data

import com.fcc.myworktime.data.database.Project
import com.fcc.myworktime.data.database.Projects
import com.fcc.myworktime.data.database.User
import com.fcc.myworktime.ui.utils.EventData
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by firta on 8/31/2017.
 * A class that will contain all the information regarding the currently logged user
 */
class UserData(
        var auth: Auth) {


    var dbUser:User? = null
    set(value) {
        field = value
        if ( value != null ) {
            userChanges.onNext(this)
        }
    }
    var dbProjects:Projects? = null
    set(value){
        field = value
        if ( value != null ) {
            projectChanges.onNext(value)
        }
    }



    private var userChanges = PublishSubject.create<UserData>()
    private var projectChanges = PublishSubject.create<Projects?>()



    fun loggedIn():Boolean{
        return auth.currentUser != null
    }

    fun getUserChangesObservable(): Observable<UserData> {
        return userChanges
    }
    fun getProjectChangesObservable(): Observable<Projects?>{
        return projectChanges
    }

    fun getEmail(): String? {
        return when {
            dbUser != null -> dbUser?.email
            loggedIn() -> auth.currentUser?.email
            else -> ""
        }

    }

    fun addNewProject(p: Project){
        dbProjects!!.projects.put(p.id, p)
        projectChanges.onNext(dbProjects!!)
    }

    fun logout() {
        dbUser = null
        dbProjects = null
    }


}