package com.fcc.myworktime.ui.mainactivity

import android.os.Bundle
import com.fcc.myworktime.data.Auth
import com.fcc.myworktime.data.UserDAO
import com.fcc.myworktime.data.UserData
import com.fcc.myworktime.data.database.User
import com.fcc.myworktime.ui.navigation.Navigator
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import com.fcc.myworktime.utils.Messages
import com.fcc.myworktime.utils.Resource
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.R.attr.delay
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


/**
 * Created by firta on 8/31/2017.
 *The presenter that will communicate with the activity
 */
class MainActivityPresenter @Inject constructor(
        val navigator:Navigator,
        val uData: UserData,
        val auth: Auth,
        val userDAO: UserDAO,
        val messages: Messages
){

    private lateinit var view:MainActivityView


    private val subscriptions = CompositeDisposable()
    private lateinit var dbUserDisposable: Disposable
    private var doubleBackToExitPressedOnce: Boolean = false


    fun bindView(view:MainActivityView){
        this.view = view
        subscriptions.add(view.viewEvent().subscribe { eventData -> viewEventCalled(eventData) })
        subscriptions.add(view.menuEvent().subscribe{ menuEvent -> menuSelected(menuEvent) })
        subscriptions.add(view.backPressedEvent().subscribe{onBackPressed()})
        subscriptions.add(uData.getUserChangesObservable().subscribe { userDataChanged() })
    }



    private fun viewEventCalled(eventData: EventData) {

        if ( eventData.event == MainView.EVENT_CREATED ){
            viewCreated(eventData.data as Bundle?)
        }else if ( eventData.event == MainView.EVENT_DESTROYED ){
            viewDestroyed()
        }

    }

    private fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            view.finish()
        } else if ( !navigator.onBackPressed() ){
            doubleBackToExitPressedOnce = true
            view.displayToast(messages.tap_again_to_exit)
        }
        Observable.just(true).delay(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map({ doubleBackToExitPressedOnce = false })
                .subscribe()
    }
    private fun menuSelected(menuEvent: EventData) {

        if ( menuEvent.event == MainActivityView.LOGOUT_MENU ){
            auth.logout()
            uData.logout()
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
                if ( uData.dbUser == null ){
                    /*if there is no dbUser then get it*/
                    dbUserDisposable = userDAO.getUser(uData.getEmail()).subscribe({ user ->
                        gotDataFromDb(user)
                    })
                }else {
                    navigator.goToProjects()
                    view.displayMenu()
                }
            }else{
                navigator.goToLogin()
                view.hideMenu()
            }
        }
    }

    private fun gotDataFromDb(userRes: Resource<User>) {
        if (userRes.status == Resource.Status.SUCCESS) {
            uData.dbUser = userRes.data!!
            navigator.goToProjects()
            view.displayMenu()
        }else {
            /*will log user out first*/
            auth.logout()
            navigator.goToLogin()
            view.hideMenu()
        }
        dbUserDisposable.dispose()
    }


    private fun userDataChanged() {

        if ( uData.loggedIn() ){
            view.displayMenu()
        }

    }


}