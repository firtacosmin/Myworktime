package com.fcc.myworktime.ui.projects.addproject

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fcc.myworktime.R
import com.fcc.myworktime.databinding.DialogAddProjectBinding
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.LifeCycleOwnerDialogFragment
import com.fcc.myworktime.ui.utils.MainView
import com.fcc.myworktime.utils.AutoClearedValue
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by firta on 9/1/2017.
 * The dialog fragment that will be displayed to add a new project
 */
class ProjectsAddDialog : LifeCycleOwnerDialogFragment(), ProjectsAddView{

    private var lifeCycleEvents = PublishSubject.create<EventData>()


    lateinit var binging:AutoClearedValue<DialogAddProjectBinding>

    @Inject lateinit var presenter:ProjectsAddPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val b = DataBindingUtil.inflate<DialogAddProjectBinding>(inflater, R.layout.dialog_add_project, container, false)
        binging = AutoClearedValue(this, b)

        presenter.bindView(this)

        lifeCycleEvents.onNext(EventData(savedInstanceState, MainView.EVENT_CREATED))
        return b.root
    }

    override fun onDestroyView() {
        lifeCycleEvents.onNext(EventData(null, MainView.EVENT_DESTROYED))
        super.onDestroyView()
    }

    override fun cancelObservable(): Observable<Any> {
        return RxView.clicks(binging.get()!!.btnCancel)
    }

    override fun okObservable(): Observable<Any> {
        return RxView.clicks(binging.get()!!.btnAddProject)
    }

    override fun getName(): String? {
        return binging.get()!!.txtName.text.toString()
    }

    override fun printError(message: String) {
        binging.get()!!.layoutName.error = message
        binging.get()!!.layoutName.isErrorEnabled = true
    }

    override fun viewEvent(): Observable<EventData> {
        return lifeCycleEvents
    }

    override fun destroy() {
        dismiss()
    }

}