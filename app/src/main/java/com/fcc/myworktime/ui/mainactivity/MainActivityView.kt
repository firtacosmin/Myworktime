package com.fcc.myworktime.ui.mainactivity

import android.os.Bundle
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import io.reactivex.Observable

/**
 * Created by firta on 8/31/2017.
 * the view implemented by the [MainActivity] to export the operations
 */
interface MainActivityView: MainView{

    companion object {
        val LOGOUT_MENU = "LOGOUT_MENU"
    }


    fun menuEvent(): Observable<EventData>


    fun displayMenu()
    fun hideMenu()


}