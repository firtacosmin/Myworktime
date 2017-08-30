package com.fcc.myworktime.data

import com.fcc.myworktime.data.database.User

/**
 * Created by firta on 8/31/2017.
 * A class that will contain all the information regarding the currently logged user
 */
class UserData(
        var auth: Auth,
        var userDAO: UserDAO,
        var projectsDAO: ProjectsDAO) {


    lateinit var dbUser:User


    fun loggedIn():Boolean{
        return auth.currentUser != null
    }

}