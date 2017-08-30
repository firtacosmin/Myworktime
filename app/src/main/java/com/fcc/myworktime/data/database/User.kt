package com.fcc.myworktime.data.database

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by firta on 8/31/2017.
 * The plain Kotlin class that describes an User
 */
@IgnoreExtraProperties
class User ( var email:String ){
    var id:String = ""
}