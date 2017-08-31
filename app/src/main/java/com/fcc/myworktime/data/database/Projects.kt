package com.fcc.myworktime.data.database

/**
 * Created by firta on 8/31/2017.
 * This is the object that will hold all of the projects of a user
 */

class Projects
{
    var projects:MutableMap<String, Project> = HashMap()
    var userid:String = ""

    var projectsID:String = ""
}