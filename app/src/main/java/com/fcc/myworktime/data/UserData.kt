package com.fcc.myworktime.data

import com.fcc.myworktime.data.database.User
import com.fcc.myworktime.ui.utils.EventData
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by firta on 8/31/2017.
 * A class that will contain all the information regarding the currently logged user
 */
class UserData(
        var auth: Auth,
        var userDAO: UserDAO,
        var projectsDAO: ProjectsDAO) {


    var dbUser:User? = null
    set(value) {
        userChanges.onNext(this)
    }

    private var userChanges = PublishSubject.create<UserData>()


    fun loggedIn():Boolean{
        return auth.currentUser != null
    }

    fun getUserChangesObservable(): Observable<UserData> {
        return userChanges
    }




}