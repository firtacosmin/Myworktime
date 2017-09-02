package com.fcc.myworktime.ui.registration

import android.util.Log
import com.fcc.myworktime.data.Auth
import com.fcc.myworktime.data.UserDAO
import com.fcc.myworktime.data.UserData
import com.fcc.myworktime.data.database.User
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.utils.Resource
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * The model that will offer the data manipulation processes for the [RegistrationPresenter]
 */
class RegistrationModel @Inject constructor(
        var userDAO: UserDAO,
        var auth: Auth,
        var uData:UserData
){

    enum class ErrorMessages{
       USER_COLLISION,
        WEAK_PASS,
        INVALID_EMAIL,
        REGISTRATION_ERROR,

        SUCCESS
    }

    private lateinit var email:String

    private var registrationEvents = PublishSubject.create<Resource<ErrorMessages>>()
    private lateinit var dbUserDisposable: Disposable

    fun getRegistrationEvents(): Observable<Resource<ErrorMessages>> {
        return registrationEvents
    }

    fun registerUser(email:String, pass:String){
        this.email = email
        /*register user in auth*/
        auth.createUserWithEmailEndPassword(email, pass, OnCompleteListener<AuthResult> {task ->authCompleted(task)})
    }

    private fun authCompleted(task: Task<AuthResult>) {
        if ( task.isSuccessful ){
            /*registration ok*/
            saveUserIntoDB()
        }else{
            // If registration fails, display a message to the user.
            when (task.exception) {
                is FirebaseAuthUserCollisionException -> showUserCollisionError()
                is FirebaseAuthWeakPasswordException -> showWeakPassError((task.exception as FirebaseAuthWeakPasswordException).message)
                is FirebaseAuthInvalidCredentialsException -> showInvalidEmailError()
                else -> showRegError()
            }
        }
    }

    private fun saveUserIntoDB() {
        userDAO.addUser(email)
        dbUserDisposable = userDAO.getUser(auth.currentUser!!.email).subscribe({ user ->
            gotDataFromDb(user)
        })

    }

    private fun gotDataFromDb(user: Resource<User>) {
        if ( user.status == Resource.Status.SUCCESS ){
            uData.dbUser = user.data
            registrationEvents.onNext(Resource.success(ErrorMessages.SUCCESS))
        }else{
            auth.logout()
            registrationEvents.onNext(Resource.error("", ErrorMessages.REGISTRATION_ERROR))
        }

    }

    private fun showRegError() {

        registrationEvents.onNext(Resource.error("", ErrorMessages.REGISTRATION_ERROR))

    }

    private fun showInvalidEmailError() {

        registrationEvents.onNext(Resource.error("", ErrorMessages.INVALID_EMAIL))

    }

    private fun showWeakPassError(message: String?) {
        var mess = message
        if ( message == null ){
            mess = ""
        }
        registrationEvents.onNext(Resource.error(mess!!, ErrorMessages.WEAK_PASS))

    }

    private fun showUserCollisionError() {


        registrationEvents.onNext(Resource.error("", ErrorMessages.USER_COLLISION))
    }


}