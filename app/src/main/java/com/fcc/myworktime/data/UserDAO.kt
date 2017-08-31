package com.fcc.myworktime.data

import com.fcc.myworktime.data.database.User
import com.fcc.myworktime.di.qualifiers.UsersDbRef
import com.fcc.myworktime.utils.Resource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * Class used to manage operation on the users table in the [FirebaseDatabase]
 */
class UserDAO @Inject constructor(@UsersDbRef val mUserDbRef: DatabaseReference){

    companion object {
        val ERROR_WHILE_GETTING_USER_FROM_DB = "ERROR_WHILE_GETTING_USER_FROM_DB"
        val ERROR_CONNECTION_CANCELED = "ERROR_CONNECTION_CANCELED"
    }

    private var dbEvent = PublishSubject.create<Resource<User>>()


    fun addUser(email:String){

        val user = User()
        user.email = email
        mUserDbRef.child(System.currentTimeMillis().toString()).setValue(user)
    }

    fun getUser(email: String?): Observable<Resource<User>> {

//        val userDb = mUserDbRef.orderByChild("email").equalTo(email)
        val userDb = mUserDbRef
        userDb.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val numChildren = dataSnapshot.childrenCount
                println("count == " + numChildren)
                var foundUser:User? = null
                dataSnapshot.children
                        .map { it.getValue(User::class.java) }
                        .forEach{
                            if ( it != null && it.email == email ) {
                               foundUser = it
                            }
                        }
                if ( foundUser != null ){
                    dbEvent.onNext(Resource.Companion.success(foundUser))
                }else{
                    dbEvent.onNext(Resource.Companion.error(ERROR_WHILE_GETTING_USER_FROM_DB, null))
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                dbEvent.onNext(Resource.error(ERROR_CONNECTION_CANCELED, null))
            }
        })
        return dbEvent

    }
}