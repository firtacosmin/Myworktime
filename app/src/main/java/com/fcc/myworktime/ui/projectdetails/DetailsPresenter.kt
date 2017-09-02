package com.fcc.myworktime.ui.projectdetails

import android.os.Bundle
import com.fcc.myworktime.ui.navigation.Navigator
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import com.fcc.myworktime.utils.Consts
import com.fcc.myworktime.utils.Messages
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by firta on 9/1/2017.
 *
 */
class DetailsPresenter @Inject constructor(
        val model:DetailsModel,
        val messages: Messages,
        val navigator: Navigator
) {


    private lateinit var view:DetailsView
    private val subscriptions = CompositeDisposable()

    fun bindView(v:DetailsView){
        view = v
        subscriptions.add(view.switchStateClickObservable().subscribe{switchStateClicked()})
        subscriptions.add(view.viewEvent().subscribe { viewState -> viewChangedState(viewState) })
        subscriptions.add(view.deleteClickedObservable().subscribe { position -> deleteWorkOnPosition(position) })
        subscriptions.add(view.addClickedObservable().subscribe { addNewWork() })
        subscriptions.add(view.itemClickedObservable().subscribe { position -> itemClicked(position) })
    }

    private fun itemClicked(position: Int) {

        val workID = model.getIdOfPosition(position)
        val data:Bundle = Bundle()
        data.putString(Consts.DETAILS_DISPLAYED_PROJECT_ID, model.projectID)
        data.putString(Consts.DETAILS_DISPLAYED_WORK_ID, workID)
        navigator.openAddWorkFragment(data)

    }

    private fun addNewWork() {

        val data:Bundle = Bundle()
        data.putString(Consts.DETAILS_DISPLAYED_PROJECT_ID, model.projectID)
        navigator.openAddWorkFragment(data)


    }

    private fun deleteWorkOnPosition(position: Int) {

        model.deleteWorkAt(position)
        view.removeItem(position)
        printTextIntoView()

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

    private fun switchStateClicked() {
        var newItem = ""
        if ( model.isWorkStarted() ){
            newItem = model.endWork()
            view.updateFirstElement(newItem)
        }else{
            newItem = model.startWork()
            view.addItemToList(newItem)
        }
        printDataIntoView()

    }

    private fun printDataIntoView() {
        val dataToPrint = model.getListOfWork()
        view.listItems(dataToPrint)
        printTextIntoView()
    }

    private fun printTextIntoView(){
        val projectName = model.projectName
        val projectTotalHours:Double = model.projectTotalHours
        if ( model.isWorkStarted() ){
            view.displayState(messages.work_state_started!!+projectName)
            view.displayCurrentWork(messages.current_work_text!!+" "+projectTotalHours+" "+messages.hours_small)
            view.displayButtonText(messages.work_btn_end!!)
        }else{
            view.displayState(messages.work_state_ended!!+projectName)
            view.displayCurrentWork(messages.current_work_text!!+" "+projectTotalHours+" "+messages.hours_small)
            view.displayButtonText(messages.work_btn_start!!)
        }
    }


}