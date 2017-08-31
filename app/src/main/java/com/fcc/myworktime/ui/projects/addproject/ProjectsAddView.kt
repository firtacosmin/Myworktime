package com.fcc.myworktime.ui.projects.addproject

import com.fcc.myworktime.ui.utils.MainView
import io.reactivex.Observable

/**
 * Created by firta on 9/1/2017.
 *
 */
interface ProjectsAddView:MainView {

    fun cancelObservable():Observable<Any>
    fun okObservable():Observable<Any>
    fun getName():String?

    fun printError(message:String)

    fun destroy()


}