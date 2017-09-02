package com.fcc.myworktime.ui.projects

import android.os.Bundle
import com.fcc.myworktime.ui.navigation.Navigator
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import com.fcc.myworktime.utils.Consts
import com.fcc.myworktime.utils.Messages
import com.fcc.myworktime.utils.MessagesTest
import com.fcc.myworktime.utils.Resource
import io.reactivex.Observable
import io.reactivex.internal.operators.observable.ObservableNever
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.ArgumentCaptor



/**
 * Created by firta on 9/3/2017.
 *
 * 1. print empty list
 * 2. print error from server
 * 3. print filled list
 * 4. click a project
 * 5. click remove project from list but not the last one
 * 5. click remove the last project from list
 * 7. click add new project
 *
 */
class ProjectsPresenterTest{
    /*To be able to pass any() to methods that have null parameter check*/
    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T
    private var messages: Messages = MessagesTest()
    private var lifeCycleEvents = PublishSubject.create<EventData>()

    private lateinit var model:ProjectsModel
    private lateinit var navigator:Navigator
    private lateinit var view:ProjectsView

    private lateinit var presenter:ProjectsPresenter

    @Before
    fun setUp() {

        model = mock(ProjectsModel::class.java)
        navigator = mock(Navigator::class.java)
        view = mock(ProjectsView::class.java)

        presenter = ProjectsPresenter(model, messages, navigator)

    }

    @After
    fun tearDown(){
        lifeCycleEvents.onNext(EventData("",MainView.EVENT_DESTROYED))
    }


    @Test
    fun test1_emptyList(){
        val projList:List<String> = ArrayList()
        val projStateOservable: PublishSubject<Resource<List<String>>> = PublishSubject.create()

        `when`(view.projectClicked()).thenReturn(Observable.never())
        `when`(view.observableFabClick()).thenReturn(Observable.never())
        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.observableDeleteItem()).thenReturn(Observable.never())
        `when`(view.observableEditItem()).thenReturn(Observable.never())
        `when`(view.observableConfirmDlgOkClick()).thenReturn(Observable.never())
        `when`(model.getProjectsObservable()).thenReturn(projStateOservable)

        presenter.bindView(view)
        lifeCycleEvents.onNext(EventData("",MainView.EVENT_ACTIVITY_ATTACHED))

        verify(view).displayLoading()
        verify(model).getProjects()
        projStateOservable.onNext(Resource.success(projList))
        verify(view).hideLoading()
        verify(view).displayMessage(messages.no_projects_message!!)
        verify(view).displayFab()
        verify(view, never()).displayList(any())

    }


    @Test
    fun test2_errorFromServer(){
        val projList:List<String> = ArrayList()
        val projStateOservable: PublishSubject<Resource<List<String>>> = PublishSubject.create()

        `when`(view.projectClicked()).thenReturn(Observable.never())
        `when`(view.observableFabClick()).thenReturn(Observable.never())
        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.observableDeleteItem()).thenReturn(Observable.never())
        `when`(view.observableEditItem()).thenReturn(Observable.never())
        `when`(view.observableConfirmDlgOkClick()).thenReturn(Observable.never())
        `when`(model.getProjectsObservable()).thenReturn(projStateOservable)

        presenter.bindView(view)
        lifeCycleEvents.onNext(EventData("",MainView.EVENT_ACTIVITY_ATTACHED))

        verify(view).displayLoading()
        verify(model).getProjects()
        projStateOservable.onNext(Resource.error("",projList))
        verify(view).hideLoading()
        verify(view).displayMessage(messages.error_getting_projects!!)
        verify(view, never()).displayFab()
        verify(view, never()).displayList(any())

    }

    @Test
    fun test3_printFilledList(){
        val projList:ArrayList<String> = ArrayList()
        projList.add("Project 1")
        projList.add("Project 2")
        projList.add("Project 3")
        val projStateOservable: PublishSubject<Resource<List<String>>> = PublishSubject.create()

        `when`(view.projectClicked()).thenReturn(Observable.never())
        `when`(view.observableFabClick()).thenReturn(Observable.never())
        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.observableDeleteItem()).thenReturn(Observable.never())
        `when`(view.observableEditItem()).thenReturn(Observable.never())
        `when`(view.observableConfirmDlgOkClick()).thenReturn(Observable.never())
        `when`(model.getProjectsObservable()).thenReturn(projStateOservable)

        presenter.bindView(view)
        lifeCycleEvents.onNext(EventData("",MainView.EVENT_ACTIVITY_ATTACHED))

        verify(view).displayLoading()
        verify(model).getProjects()
        projStateOservable.onNext(Resource.success(projList))
        verify(view).hideLoading()
        verify(view).displayList(projList)
        verify(view).displayFab()
        verify(view).displayMessage("")

    }

    @Test
    fun test4_clickAProject(){
        val projList:ArrayList<String> = ArrayList()
        projList.add("Project 1")
        projList.add("Project 2")
        projList.add("Project 3")
        val projectID = "projectID"
        val projStateOservable: PublishSubject<Resource<List<String>>> = PublishSubject.create()
        val click: PublishSubject<Int> = PublishSubject.create()


        `when`(view.projectClicked()).thenReturn(click)
        `when`(view.observableFabClick()).thenReturn(Observable.never())
        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.observableDeleteItem()).thenReturn(Observable.never())
        `when`(view.observableEditItem()).thenReturn(Observable.never())
        `when`(view.observableConfirmDlgOkClick()).thenReturn(Observable.never())
        `when`(model.getProjectsObservable()).thenReturn(projStateOservable)
        `when`(model.getProjectID(1)).thenReturn(projectID)

        presenter.bindView(view)
        lifeCycleEvents.onNext(EventData("",MainView.EVENT_ACTIVITY_ATTACHED))

        verify(view).displayLoading()
        verify(model).getProjects()
        projStateOservable.onNext(Resource.success(projList))
        verify(view).hideLoading()
        verify(view).displayList(projList)
        verify(view).displayFab()
        verify(view).displayMessage("")
        click.onNext(1)
        verify(navigator).openProjectDetails(projectID)


    }


    @Test
    fun test5_removeAProject_not_last(){
        val projList:ArrayList<String> = ArrayList()
        projList.add("Project 1")
        projList.add("Project 2")
        projList.add("Project 3")
        val projectID = "projectID"
        val posToDelete = 1
        val projStateOservable: PublishSubject<Resource<List<String>>> = PublishSubject.create()
        val delete: PublishSubject<Int> = PublishSubject.create()
        val removeConfirm: PublishSubject<Any> = PublishSubject.create()



        `when`(view.projectClicked()).thenReturn(Observable.never())
        `when`(view.observableFabClick()).thenReturn(Observable.never())
        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.observableDeleteItem()).thenReturn(delete)
        `when`(view.observableEditItem()).thenReturn(Observable.never())
        `when`(view.observableConfirmDlgOkClick()).thenReturn(removeConfirm)
        `when`(model.getProjectsObservable()).thenReturn(projStateOservable)
        `when`(model.getProjectID(1)).thenReturn(projectID)
        `when`(model.getDataSize()).thenReturn(2)
        presenter.bindView(view)
        lifeCycleEvents.onNext(EventData("",MainView.EVENT_ACTIVITY_ATTACHED))

        verify(view).displayLoading()
        verify(model).getProjects()
        projStateOservable.onNext(Resource.success(projList))
        verify(view).hideLoading()
        verify(view).displayList(projList)
        verify(view).displayFab()
        verify(view).displayMessage("")

        delete.onNext(posToDelete)
        verify(view).displayConfirmationDlg(messages.remove_project_dialog_tile!!, messages.remove_project_dialog_message!!+projList[posToDelete])
        removeConfirm.onNext("")
        verify(model).deleteProject(posToDelete)
        verify(view).deleteItemAtPosition(posToDelete)
        verify(view, never()).displayMessage(messages.no_projects_message!!)


    }


    @Test
    fun test7_clickAddNewProject(){
        val projList:ArrayList<String> = ArrayList()
        projList.add("Project 1")
        projList.add("Project 2")
        projList.add("Project 3")
        val projectID = "projectID"
        val posToDelete = 1
        val projStateOservable: PublishSubject<Resource<List<String>>> = PublishSubject.create()
        val delete: PublishSubject<Int> = PublishSubject.create()
        val removeConfirm: PublishSubject<Any> = PublishSubject.create()



        `when`(view.projectClicked()).thenReturn(Observable.never())
        `when`(view.observableFabClick()).thenReturn(Observable.never())
        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.observableDeleteItem()).thenReturn(delete)
        `when`(view.observableEditItem()).thenReturn(Observable.never())
        `when`(view.observableConfirmDlgOkClick()).thenReturn(removeConfirm)
        `when`(model.getProjectsObservable()).thenReturn(projStateOservable)
        `when`(model.getProjectID(1)).thenReturn(projectID)
        `when`(model.getDataSize()).thenReturn(0)

        presenter.bindView(view)
        lifeCycleEvents.onNext(EventData("",MainView.EVENT_ACTIVITY_ATTACHED))

        verify(view).displayLoading()
        verify(model).getProjects()
        projStateOservable.onNext(Resource.success(projList))
        verify(view).hideLoading()
        verify(view).displayList(projList)
        verify(view).displayFab()
        verify(view).displayMessage("")

        delete.onNext(posToDelete)
        verify(view).displayConfirmationDlg(messages.remove_project_dialog_tile!!, messages.remove_project_dialog_message!!+projList[posToDelete])
        removeConfirm.onNext("")
        verify(model).deleteProject(posToDelete)
        verify(view).deleteItemAtPosition(posToDelete)
        verify(view).displayMessage(messages.no_projects_message!!)


    }


    @Test
    fun test6_removeTheLastProject(){
        val projList:ArrayList<String> = ArrayList()
        projList.add("Project 1")
        projList.add("Project 2")
        projList.add("Project 3")
        val projectID = "projectID"
        val projStateOservable: PublishSubject<Resource<List<String>>> = PublishSubject.create()
        val click: PublishSubject<Any> = PublishSubject.create()


        `when`(view.projectClicked()).thenReturn(Observable.never())
        `when`(view.observableFabClick()).thenReturn(click)
        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.observableDeleteItem()).thenReturn(Observable.never())
        `when`(view.observableEditItem()).thenReturn(Observable.never())
        `when`(view.observableConfirmDlgOkClick()).thenReturn(Observable.never())
        `when`(model.getProjectsObservable()).thenReturn(projStateOservable)
        `when`(model.getProjectID(1)).thenReturn(projectID)

        presenter.bindView(view)
        lifeCycleEvents.onNext(EventData("",MainView.EVENT_ACTIVITY_ATTACHED))

        click.onNext("")

        verify(navigator).openAddProjectDialog()


    }
}