package com.fcc.myworktime.ui.projects

import com.fcc.myworktime.ui.utils.MainView
import io.reactivex.Observable

/**
 * Created by firta on 8/31/2017.
 * The view that will offer the ui actions of the projects functionality
 */
interface ProjectsView : MainView {

    fun projectClicked():Observable<Int>
    fun observerFabClick(): Observable<Any>


    fun displayLoading()
    fun hideLoading()
    fun displayMessage(message:String)
    fun displayList(items:List<String>)
    fun addProject(title:String)
    fun displayFab()


}