package com.fcc.myworktime.data

import com.fcc.myworktime.data.database.User
import com.fcc.myworktime.di.qualifiers.UsersDbRef
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * Class used to manage operation on the users table in the [FirebaseDatabase]
 */
class UserDAO @Inject constructor(@UsersDbRef val mUserDbRef: DatabaseReference){


    fun addUser(email:String){

        val user = User(email)
        mUserDbRef.child(System.currentTimeMillis().toString()).setValue(user)
    }
}