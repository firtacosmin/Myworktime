package com.fcc.myworktime.ui.projectdetails

import com.fcc.myworktime.ui.utils.MainView
import io.reactivex.Observable

/**
 * Created by firta on 9/1/2017.
 */
interface DetailsView:MainView {

    fun switchStateClickObservable():Observable<Any>
    fun deleteClickedObservable():Observable<Int>
    fun editClickedObservable():Observable<Int>
    fun addClickedObservable():Observable<Any>
    fun itemClickedObservable():Observable<Int>

    fun displayState(state:String)
    fun displayCurrentWork(work:String)
    fun displayButtonText(text:String)
    fun listItems(items :List<String>)
    fun addItemToList(item:String)
    fun updateFirstElement(newItem: String)
    fun removeItem(position:Int)

}