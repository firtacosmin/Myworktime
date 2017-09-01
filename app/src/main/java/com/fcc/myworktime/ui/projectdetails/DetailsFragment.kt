package com.fcc.myworktime.ui.projectdetails

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
 */
class DetailsFragment: LifeCycleOwnerFragment(),DetailsView {



    companion object {
        fun getInstance(data:Bundle):DetailsFragment{
            val f = DetailsFragment()
            f.arguments = data
            return f
        }
    }


    lateinit var binding :AutoClearedValue<FragmentDetailsBinding>
    private var lifeCycleEvents = PublishSubject.create<EventData>()
    @Inject lateinit var  presenter : DetailsPresenter

    private lateinit var adapter:TextListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val b = DataBindingUtil.inflate<FragmentDetailsBinding>(inflater, R.layout.fragment_details, container, false)
        binding = AutoClearedValue(this, b)

        presenter.bindView(this)

        adapter = TextListAdapter()
        b.listWork.adapter = adapter


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

    override fun addItemToList(item: String) {
        adapter.addItem(item)
    }
    override fun updateLastItem(newItem: String) {
        adapter.updateLastItem(newItem)

    }

    override fun viewEvent(): Observable<EventData> {
        return lifeCycleEvents
    }
}