package com.fcc.myworktime.ui.login

import com.fcc.myworktime.data.Auth
import com.fcc.myworktime.data.UserDAO
import com.fcc.myworktime.data.UserData
import com.fcc.myworktime.data.database.User
import com.fcc.myworktime.utils.Resource
import com.google.android.gms.tasks.OnCompleteListener
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * The class that will provide the interaction of the [LoginPresenter] with the data
 */
open class LoginModel @Inject constructor(
        var auth:Auth,
        private var userDAO: UserDAO,
        private var uData:UserData
){

    private var loginEvents = PublishSubject.create<Resource<String>>()
    private lateinit var dbUserDisposable:Disposable


    fun login(email:String, pass:String):Observable<Resource<String>>{
        auth.login(email, pass, OnCompleteListener {
            task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                loginOk()
            } else {
                // If sign in fails, display a message to the user.
                announceErrorLogin()
            }
        })
        announceLoading()
        return loginEvents
    }

    private fun announceLoading() {
        loginEvents.onNext(Resource.loading(""))
    }

    private fun announceErrorLogin() {
        loginEvents.onNext(Resource.error("", ""))
    }

    private fun loginOk() {
        /*if login successful then get the user data from db and save it*/
        dbUserDisposable = userDAO.getUser(auth.currentUser!!.email).subscribe({ user ->
            gotDataFromDb(user)
        })
    }

    private fun gotDataFromDb(userRes: Resource<User>) {
        if (userRes.status == Resource.Status.SUCCESS) {
            uData.dbUser = userRes.data!!
            announceSuccessfulLogin()
        }else {
            /*will log user out first*/
            auth.logout()
            announceErrorLogin()
        }
        dbUserDisposable.dispose()
    }

    private fun announceSuccessfulLogin() {

        loginEvents.onNext(Resource.success(""))

    }

}