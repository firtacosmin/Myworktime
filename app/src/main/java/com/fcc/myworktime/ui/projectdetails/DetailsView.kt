package com.fcc.myworktime.ui.projectdetails

import com.fcc.myworktime.ui.utils.MainView
import io.reactivex.Observable

/**
 * Created by firta on 9/1/2017.
 */
interface DetailsView:MainView {

    fun switchStateClickObservable():Observable<Any>


    fun displayState(state:String)
    fun displayButtonText(text:String)
    fun listItems(items :List<String>)
    fun addItemToList(item:String)

}