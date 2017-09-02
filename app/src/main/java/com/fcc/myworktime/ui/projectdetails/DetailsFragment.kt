package com.fcc.myworktime.ui.projectdetails

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.*
import com.fcc.myworktime.R
import com.fcc.myworktime.databinding.FragmentDetailsBinding
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.LifeCycleOwnerFragment
import com.fcc.myworktime.ui.utils.MainView
import com.fcc.myworktime.ui.utils.TextListAdapter
import com.fcc.myworktime.utils.AutoClearedValue
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by firta on 9/1/2017.
 *
 */
class DetailsFragment: LifeCycleOwnerFragment(),DetailsView {
    companion object {


        fun getInstance(data:Bundle):DetailsFragment{
            val f = DetailsFragment()
            f.arguments = data
            return f
        }
    }
    private lateinit var addMenu: MenuItem


    lateinit var binding :AutoClearedValue<FragmentDetailsBinding>
    private var lifeCycleEvents = PublishSubject.create<EventData>()
    @Inject lateinit var  presenter : DetailsPresenter

    private lateinit var adapter:TextListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val b = DataBindingUtil.inflate<FragmentDetailsBinding>(inflater, R.layout.fragment_details, container, false)
        binding = AutoClearedValue(this, b)


        adapter = TextListAdapter()
        b.listWork.adapter = adapter

        presenter.bindView(this)

        val eventBundle = Bundle()
        if ( savedInstanceState != null ) {
            eventBundle.putAll(savedInstanceState)
        }
        eventBundle.putAll(arguments)
        lifeCycleEvents.onNext(EventData(eventBundle, MainView.EVENT_CREATED))

        return b.root
    }


    override fun onDestroyView() {
        lifeCycleEvents.onNext(EventData(null, MainView.EVENT_DESTROYED))
        super.onDestroyView()
    }

    override fun switchStateClickObservable(): Observable<Any> {
        return RxView.clicks(binding.get()!!.btnSwitchState)
    }

    override fun displayState(state: String) {
        binding.get()!!.txtCurrentState.text = state
    }

    override fun displayButtonText(text: String) {
        binding.get()!!.btnSwitchState.text = text
    }

    override fun listItems(items: List<String>) {
        adapter.setItems(items)

    }

    override fun removeItem(position: Int) {

        adapter.removeItem(position)

    }
    override fun addItemToList(item: String) {
        adapter.addItemInFront(item)
        binding.get()!!.listWork.scrollToPosition(0)
    }

    override fun updateFirstElement(newItem: String) {
        adapter.updateFirstItem(newItem)

    }
    override fun viewEvent(): Observable<EventData> {
        return lifeCycleEvents
    }

    override fun editClickedObservable(): Observable<Int> {
        return adapter.editClickedEvent
    }
    override fun addClickedObservable(): Observable<Any> {
        return RxView.clicks(binding.get()!!.fab)
    }

    override fun deleteClickedObservable(): Observable<Int> {
        return adapter.deleteClickedEvent
    }
}