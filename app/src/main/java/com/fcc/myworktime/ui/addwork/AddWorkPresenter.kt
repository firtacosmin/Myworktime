package com.fcc.myworktime.ui.addwork

import android.os.Bundle
import com.fcc.myworktime.ui.navigation.Navigator
import com.fcc.myworktime.ui.projectdetails.DetailsFragment
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import com.fcc.myworktime.utils.Messages
import com.fcc.myworktime.utils.Utils
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by firta on 9/2/2017.
 *
 */

class AddWorkPresenter @Inject constructor(
        val messages: Messages,
        val model:AddWorkModel,
        val navigator: Navigator

){



    private lateinit var view:AddWorkView
    private val subscriptions = CompositeDisposable()


    fun bindView(v:AddWorkView){
        view = v
        subscriptions.add(view.cancelClickObservable().subscribe { cancelClicked() })
        subscriptions.add(view.okClickObservable().subscribe { okClicked() })
        subscriptions.add(view.dateClickObservable().subscribe { dateClicked() })
        subscriptions.add(view.viewEvent().subscribe { viewData -> viewChangedState(viewData) })
        subscriptions.add(view.datePikedObservable().subscribe { newDate -> datePiked(newDate) })
    }

    private fun datePiked(newDate: Long) {
        view.setDate(Utils.getDate(newDate))
    }

    private fun viewChangedState(viewState: EventData) {

        when{
            viewState.event == MainView.EVENT_CREATED -> {
                model.getDataFromBundle(viewState.data as Bundle)

                printDataIntoView()

            }
            viewState.event == MainView.EVENT_DESTROYED -> {
                subscriptions.clear()
            }
        }

    }

    private fun printDataIntoView() {

        view.setProjectName(model.getProjectName())
        if ( model.getWorkDate().isEmpty() ) {
            view.setDate(Utils.getSmallDate(System.currentTimeMillis()))
        }else{
            view.setDate(model.getWorkDate())
        }
        view.setDescription(model.getWorkDesc())
        view.setActivity(model.getWorkActivity())
        view.setHours(model.getWorkHours())

    }

    private fun dateClicked() {

        view.printDatePicker(messages.date_piker_title!!)

    }

    private fun okClicked() {

        var err = false

        if ( view.getDate().isEmpty() ){
            err = true
            view.setDateError(messages.field_empty_error!!)
        }else{
            view.setDateError("")
        }

        if ( view.getActivityText().isEmpty() ){
            err = true
            view.setActivityError(messages.field_empty_error!!)
        }else{
            view.setActivityError("")
        }
        if ( view.getHours().isEmpty() ){
            err = true
            view.setHoursError(messages.field_empty_error!!)
        }else{
            view.setHoursError("")
        }

        if ( !err ){
            saveWork()
        }


    }

    private fun saveWork() {

        model.addWork(
                Utils.getMillis(view.getDate()),
                view.getActivityText(),
                view.getHours().toDouble(),
                view.getDescription()
        )
        cancelClicked()

    }

    private fun cancelClicked() {

        navigator.onBackPressed()

    }

}