package com.fcc.myworktime.ui.projects.addproject

import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import com.fcc.myworktime.utils.Messages
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by firta on 9/1/2017.
 * the presenter for [ProjectsAddDialog]
 */
class ProjectsAddPresenter @Inject constructor(
        val model:ProjectsAddModel,
        val messages: Messages
) {

    lateinit var view:ProjectsAddView
    private val subscriptions = CompositeDisposable()


    fun bindView(view:ProjectsAddView){
        this.view = view
        subscriptions.add(view.cancelObservable().subscribe { cancelClicked() })
        subscriptions.add(view.okObservable().subscribe{okClicked()})
        subscriptions.add(view.viewEvent().subscribe{event -> viewEvent(event)})
    }

    private fun viewEvent(event: EventData) {

        if ( event.event == MainView.EVENT_DESTROYED ){
            subscriptions.clear()
        }

    }

    private fun okClicked() {

        if ( view.getName() == null || view.getName()!!.isEmpty() ){
            view.printError(messages.error_project_name_empty!!)
        }else{
            model.addProject(view.getName()!!)
            view.destroy()
        }

    }

    private fun cancelClicked() {

        view.destroy()

    }


}