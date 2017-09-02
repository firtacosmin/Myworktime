package com.fcc.myworktime.ui.registration

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
 * The presenter for the [RegistrationView]
 */
class RegistrationPresenter @Inject constructor(
        val navigator:Navigator,
        val model:RegistrationModel,
        val messages: Messages
){
    private lateinit var view: RegistrationView
    private val subscriptions = CompositeDisposable()

    private var modelDisposable: Disposable? = null

    fun bindView(v:RegistrationView){
        view = v

        subscriptions.add(view.registerObservable().subscribe{registerClicked()})
        subscriptions.add(view.viewEvent().subscribe { viewData -> viewEvent(viewData) })



    }

    private fun viewEvent(viewData: EventData) {

        when{
            viewData.event == MainView.EVENT_DESTROYED->{
                subscriptions.clear()
                if ( modelDisposable != null && !modelDisposable!!.isDisposed ){
                    modelDisposable?.dispose()
                }
            }
        }

    }

    private fun registerClicked() {

        var error = false
        if (view.getEmail().isEmpty()) {
            error = true
            view.printEmailError(messages.field_empty_error!!)
        } else {
            view.printEmailError("")
        }

        when {
            view.getPassword().isEmpty() -> {
                error = true
                view.printPassError(messages.field_empty_error!!)
            }
            view.getRePassword().isEmpty() -> {
                error = true
                view.printPassError("")
                view.printRePassError(messages.field_empty_error!!)
            }
            view.getPassword() != view.getRePassword() -> {
                error = true
                view.printPassError("")
                view.printRePassError(messages.passwords_not_thesame!!)
            }
            else -> {
                view.printPassError("")
                view.printRePassError("")
            }
        }

        if ( !error ){
            view.printLoading()
            if ( modelDisposable != null && !modelDisposable!!.isDisposed ){
                modelDisposable?.dispose()
            }
            modelDisposable = model.getRegistrationEvents().subscribe { response -> responseFromRegistration(response) }
            model.registerUser(view.getEmail(), view.getPassword())
        }




    }

    private fun responseFromRegistration(response: Resource<RegistrationModel.ErrorMessages>) {
        view.hideLoading()
        when(response.data){

            RegistrationModel.ErrorMessages.USER_COLLISION -> printUserCollisionError()
            RegistrationModel.ErrorMessages.WEAK_PASS -> printWeakPassError()
            RegistrationModel.ErrorMessages.INVALID_EMAIL -> printInvalidEmail()
            RegistrationModel.ErrorMessages.REGISTRATION_ERROR -> printRegistrationError()
            RegistrationModel.ErrorMessages.SUCCESS -> registrationOK()
        }

    }

    private fun registrationOK() {
        navigator.goToProjects()
    }

    private fun printRegistrationError() {
        view.printGeneralError(messages.error_while_registering!!)
    }

    private fun printInvalidEmail() {
        view.printGeneralError(messages.invalid_email_error!!)
    }

    private fun printWeakPassError() {
        view.printGeneralError(messages.weak_pass_error!!)
    }

    private fun printUserCollisionError() {
        view.printGeneralError(messages.user_collision_error!!)
    }

}