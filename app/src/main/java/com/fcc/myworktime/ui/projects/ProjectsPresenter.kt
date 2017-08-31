package com.fcc.myworktime.ui.projects

import com.fcc.myworktime.ui.navigation.Navigator
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import com.fcc.myworktime.utils.Messages
import com.fcc.myworktime.utils.Resource
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * the presenter for the [ProjectsView]
 */
class ProjectsPresenter @Inject constructor(
        val model:ProjectsModel,
        val messages: Messages,
        val navigator: Navigator
){

    lateinit var view:ProjectsView
    private val subscriptions = CompositeDisposable()


    fun bindView(v:ProjectsView){
        view = v
        subscriptions.add(v.projectClicked().subscribe { position -> clickedProject(position) })
        subscriptions.add(v.observerFabClick().subscribe { _ -> fabClicked() })
        subscriptions.add(v.viewEvent().subscribe{eventData -> viewStateChange(eventData)})
        subscriptions.add(model.projectsObservable.subscribe{result -> gotData(result)})

    }

    private fun viewStateChange(eventData: EventData) {
        if (eventData.event == MainView.EVENT_DESTROYED){
            viewDestroyed()
        } else if ( eventData.event == MainView.EVENT_ACTIVITY_ATTACHED ){
            model.getProjects()
            view.displayLoading()
        }
    }

    private fun gotData(result: Resource<List<String>>) {

        if ( result.status == Resource.Status.SUCCESS ){
            if ( result.data!!.isEmpty() ){
                view.hideLoading()
                view.displayMessage(messages.no_projects_message!!)
            }else {
                view.hideLoading()
                view.displayList(result.data)
                view.displayFab()
            }
        }else {
            view.hideLoading()
            view.displayMessage(messages.error_getting_projects!!)
        }

    }

    private fun viewDestroyed() {
        subscriptions.clear()
        model.clear()
    }


    private fun fabClicked() {

        navigator.openAddProjectDialog()

    }

    private fun clickedProject(position: Int) {

    }


}