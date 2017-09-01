package com.fcc.myworktime.ui.login

import com.fcc.myworktime.ui.navigation.Navigator
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import com.fcc.myworktime.utils.Messages
import com.fcc.myworktime.utils.Resource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * the presenter that will manage the [LoginView]
 */
class LoginPresenter @Inject constructor(
        var navigator: Navigator,
        var model:LoginModel,
        var messages: Messages
){

    private lateinit var view:LoginView
    private val subscriptions = CompositeDisposable()

    private lateinit var loginModelDisposable:Disposable


    fun bindView(v:LoginView)
    {
        view = v
        subscriptions.add(bindViewEvents())
        subscriptions.add(bindLoginClick())
        subscriptions.add(bindRegClick())
    }

    private fun bindViewEvents(): Disposable {
        return view.viewEvent().subscribe {
            eventData -> newViewEvent(eventData)
        }
    }

    private fun bindRegClick(): Disposable {

        return view.observerRegisterClick().subscribe {
            registerClicked()
        }
    }


    private fun bindLoginClick(): Disposable {

        return view.observerLoginClick().subscribe({
            loginClicked()
        })

    }

    private fun newViewEvent(eventData: EventData) {

        if ( eventData.event == MainView.EVENT_DESTROYED ){
            subscriptions.clear()
        }

    }

    private fun loginClicked() {

        loginModelDisposable = model.login(view.getEmail(), view.getPassword()).subscribe({
            loginData -> loginResponse(loginData)
        })
        view.printLoading()

    }

    private fun loginResponse(loginData: Resource<String>) {

        when {
            loginData.status == Resource.Status.SUCCESS -> loginSuccessful()
            loginData.status == Resource.Status.ERROR -> loginWithError()
            loginData.status == Resource.Status.LOADING -> printLoading()
        }

    }

    private fun printLoading() {

        view.printLoading()
    }

    private fun loginWithError() {

        view.printError(messages.loginErrorMessage)

    }

    private fun loginSuccessful() {
        navigator.goToProjects()
    }

    private fun registerClicked() {

        navigator.goToRegistration()

    }


}