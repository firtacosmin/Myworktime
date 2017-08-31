package com.fcc.myworktime.ui.login

import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import io.reactivex.Observable

/**
 * Created by firta on 8/31/2017.
 * The interface that will provide the actions that can be executed on the login view
 */
interface LoginView :MainView{
    fun observerLoginClick(): Observable<Any>
    fun observerRegisterClick(): Observable<Any>
    fun getEmail(): String
    fun getPassword(): String
    fun printError(loginErrorMessage: String?)
    fun printLoading()

}