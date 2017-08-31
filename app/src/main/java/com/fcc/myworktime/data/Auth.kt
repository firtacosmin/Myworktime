package com.fcc.myworktime.data

import com.fcc.myworktime.di.AppScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 */
@AppScope
class Auth @Inject
constructor(private val fireAuth: FirebaseAuth) {

    fun login(email: String, password: String, listener: OnCompleteListener<AuthResult>) {
        fireAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener)
    }

    fun createUserWithEmailEndPassword(email: String, password: String, listener: OnCompleteListener<AuthResult>) {
        fireAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener)

    }

    fun signOut() {
        fireAuth.signOut()
    }

    val currentUser: FirebaseUser?
        get() = fireAuth.currentUser

    fun logout() {

        fireAuth.signOut()

    }



}
