package com.fcc.myworktime.ui.mainactivity

import android.os.Bundle
import com.fcc.myworktime.data.Auth
import com.fcc.myworktime.data.UserData
import com.fcc.myworktime.ui.navigation.Navigator
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable



/**
 * Created by firta on 8/31/2017.
 *The presenter that will communicate with the activity
 */
class MainActivityPresenter @Inject constructor(
        var navigator:Navigator,
        var uData: UserData,
        var auth: Auth
){

    private lateinit var view:MainActivityView


    private val subscriptions = CompositeDisposable()


    fun bindView(view:MainActivityView){
        this.view = view
        subscriptions.add(view.viewEvent().subscribe { eventData -> viewEventCalled(eventData) })
        subscriptions.add(view.menuEvent().subscribe{ menuEvent -> menuSelected(menuEvent) })
        subscriptions.add(uData.getUserChangesObservable().subscribe { userDataChanged() })
    }


    private fun viewEventCalled(eventData: EventData) {

        if ( eventData.event == MainView.EVENT_CREATED ){
            viewCreated(eventData.data as Bundle?)
        }else if ( eventData.event == MainView.EVENT_DESTROYED ){
            viewDestroyed()
        }

    }

    private fun menuSelected(menuEvent: EventData) {

        if ( menuEvent.event == MainActivityView.LOGOUT_MENU ){
            auth.logout()
            navigator.goToLogin()
            view.hideMenu()
        }

    }

    private fun viewDestroyed() {
        subscriptions.clear()
    }

    private fun viewCreated(bundle: Bundle?) {
        if ( bundle == null ){
            if ( uData.loggedIn() ){
                navigator.goToProjects()
                view.displayMenu()
            }else{
                navigator.goToLogin()
                view.hideMenu()
            }
        }
    }


    private fun userDataChanged() {

        if ( uData.loggedIn() ){
            view.displayMenu()
        }

    }


}