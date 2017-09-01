package com.fcc.myworktime.ui.projectdetails

import android.os.Bundle
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by firta on 9/1/2017.
 *
 */
class DetailsPresenter @Inject constructor(
        val model:DetailsModel
) {


    private lateinit var view:DetailsView
    private val subscriptions = CompositeDisposable()

    fun bindView(v:DetailsView){
        view = v
        subscriptions.add(view.switchStateClickObservable().subscribe{switchStateClicked()})
        subscriptions.add(view.viewEvent().subscribe { viewState -> viewChangedState(viewState) })
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


    }

    private fun printDataIntoView() {
        val dataToPrint = model.getListOfWork()
        view.listItems(dataToPrint)
    }


}