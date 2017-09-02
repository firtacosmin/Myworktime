package com.fcc.myworktime.ui.addwork

import com.fcc.myworktime.ui.utils.MainView
import io.reactivex.Observable

/**
 * Created by firta on 9/2/2017.
 *
 */
interface AddWorkView: MainView {

    fun okClickObservable(): Observable<Any>
    fun cancelClickObservable(): Observable<Any>
    fun dateClickObservable(): Observable<Any>


    fun getDate():String
    fun getActivityText():String
    fun getDescription():String
    fun getHours():String

    fun setProjectName(projectName:String)
    fun setDate(date:String)

    fun setDateError(err:String)
    fun setActivityError(err:String)
    fun setDescriptionError(err:String)
    fun setHoursError(err:String)

    fun printDatePicker(dialogTitle:String)
    fun datePikedObservable():Observable<Long>

}