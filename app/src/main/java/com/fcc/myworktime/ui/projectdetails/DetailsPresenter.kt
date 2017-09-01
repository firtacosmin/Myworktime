package com.fcc.myworktime.ui.projectdetails

import android.os.Bundle
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import com.fcc.myworktime.utils.Messages
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by firta on 9/1/2017.
 *
 */
class DetailsPresenter @Inject constructor(
        val model:DetailsModel,
        val messages: Messages
) {


    private lateinit var view:DetailsView
    private val subscriptions = CompositeDisposable()

    fun bindView(v:DetailsView){
        view = v
        subscriptions.add(view.switchStateClickObservable().subscribe{switchStateClicked()})
        subscriptions.add(view.viewEvent().subscribe { viewState -> viewChangedState(viewState) })
        subscriptions.add(view.deleteClickedObservable().subscribe { position -> deleteWorkOnPosition(position) })
    }

    private fun deleteWorkOnPosition(position: Int) {

        model.deleteWorkAt(position)
        view.removeItem(position)

    }

    private fun viewChangedState(viewState: EventData) {

        when{
            viewState.event == MainView.EVENT_CREATED -> {
                model.getDataFromBundle(viewState.data as Bundle)

                printDataIntoView()

            }
        }

    }

    private fun switchStateClicked() {

        if ( model.isWorkStarted() ){
            val newItem:String = model.endWork()
            view.updateFirstElement(newItem)
            view.displayState(messages.work_state_ended!!)
            view.displayButtonText(messages.work_btn_start!!)
        }else{
            val newItem:String = model.startWork()
            view.addItemToList(newItem)
            view.displayState(messages.work_state_started!!)
            view.displayButtonText(messages.work_btn_end!!)
        }

    }

    private fun printDataIntoView() {
        val dataToPrint = model.getListOfWork()
        view.listItems(dataToPrint)
        if ( model.isWorkStarted() ){
            view.displayState(messages.work_state_started!!)
            view.displayButtonText(messages.work_btn_end!!)
        }else{
            view.displayState(messages.work_state_ended!!)
            view.displayButtonText(messages.work_btn_start!!)
        }
    }


}