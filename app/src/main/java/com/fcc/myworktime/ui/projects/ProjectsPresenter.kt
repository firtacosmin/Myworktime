package com.fcc.myworktime.ui.projects

import android.os.Bundle
import com.fcc.myworktime.ui.navigation.Navigator
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import com.fcc.myworktime.utils.Consts
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

    private var positionToDelete:Int = -1

    /**
     * a copy of the elements that are displayed
     */
    private lateinit var displayedElements:List<String>


    fun bindView(v:ProjectsView){
        view = v
        subscriptions.add(v.projectClicked().subscribe { position -> clickedProject(position) })
        subscriptions.add(v.observableFabClick().subscribe { _ -> fabClicked() })
        subscriptions.add(v.viewEvent().subscribe{eventData -> viewStateChange(eventData)})
        subscriptions.add(model.projectsObservable.subscribe{result -> gotData(result)})
        subscriptions.add(v.observableDeleteItem().subscribe { position -> removeItem(position) })
        subscriptions.add(v.observableEditItem().subscribe{position -> editItem(position)})
        subscriptions.add(v.observableConfirmDlgOkClick().subscribe { removalConfirmed() })
    }

    private fun removalConfirmed() {
        model.deleteProject(positionToDelete)
        view.deleteItemAtPosition(positionToDelete)
        if ( model.getDataSize() == 0){
            view.displayMessage(messages.no_projects_message!!)
        }

    }

    private fun editItem(position: Int) {


    }

    private fun removeItem(position: Int) {
        positionToDelete = position
        view.displayConfirmationDlg(messages.remove_project_dialog_tile!!, messages.remove_project_dialog_message!!+displayedElements[position])


    }

    private fun viewStateChange(eventData: EventData) {
        if (eventData.event == MainView.EVENT_DESTROYED){
            viewDestroyed()
        } else if ( eventData.event == MainView.EVENT_ACTIVITY_ATTACHED ){
            view.displayLoading()
            model.getProjects()
        }
    }

    private fun gotData(result: Resource<List<String>>) {

        if ( result.status == Resource.Status.SUCCESS ){
            if ( result.data!!.isEmpty() ){
                view.hideLoading()
                view.displayMessage(messages.no_projects_message!!)
                view.displayFab()
            }else {
                displayedElements = result.data
                view.hideLoading()
                view.displayList(displayedElements)
                view.displayFab()
                view.displayMessage("")

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

        val project:String = model.getProjectID(position)
        val data = Bundle()
        data.putString(Consts.DETAILS_DISPLAYED_PROJECT_ID, project)
        navigator.openProjectDetails(data)

    }


}