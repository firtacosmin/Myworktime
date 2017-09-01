package com.fcc.myworktime.data.database

/**
 * Created by firta on 8/31/2017.
 * This is the class that holds the details of a project
 */
class Project {

    companion object {
        val DB_OBJECT_NAME = "projects"
        val DB_OBJECT_PROJECT_NAME_CHILD = "name"
    }

    var name:String = ""
    var work:MutableMap<String,Work> = HashMap<String,Work>()
    var id:String = ""
}