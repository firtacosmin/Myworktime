package com.fcc.myworktime.ui.registration

import com.fcc.myworktime.ui.utils.MainView
import io.reactivex.Observable


/**
 * Created by firta on 8/31/2017.
 * the interface used by the [RegistrationFragment] to interact with the [RegistrationPresenter]
 */
interface RegistrationView :MainView{

    fun registerObservable(): Observable<Any>

    fun getEmail():String
    fun getPassword():String
    fun getRePassword():String


    fun printEmailError(err:String)
    fun printPassError(err:String)
    fun printRePassError(err:String)
    fun printGeneralError(err:String)

    fun printLoading()
    fun hideLoading()

}