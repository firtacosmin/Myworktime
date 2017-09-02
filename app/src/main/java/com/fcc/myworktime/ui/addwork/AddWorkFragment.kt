package com.fcc.myworktime.ui.addwork

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.*
import com.fcc.myworktime.R
import com.fcc.myworktime.databinding.FragmentAddWorkBinding
import com.fcc.myworktime.ui.projectdetails.DetailsFragment
import com.fcc.myworktime.ui.utils.DatePickerFragment
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.LifeCycleOwnerFragment
import com.fcc.myworktime.ui.utils.MainView
import com.fcc.myworktime.utils.AutoClearedValue
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by firta on 9/2/2017.
 *
 */
class AddWorkFragment : LifeCycleOwnerFragment(), AddWorkView {

    companion object {
        fun getInstance(data:Bundle): AddWorkFragment {
            val f = AddWorkFragment()
            f.arguments = data
            return f
        }
    }
    private lateinit var okMenu: MenuItem


    private lateinit var cancelMenu: MenuItem
    lateinit var binding : AutoClearedValue<FragmentAddWorkBinding>

    private val okClickEvent = PublishSubject.create<Any>()
    private val cancelClickEvent = PublishSubject.create<Any>()
    private var lifeCycleEvents = PublishSubject.create<EventData>()
    @Inject lateinit var  presenter : AddWorkPresenter

    private val datePikerDialog = DatePickerFragment()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val b = DataBindingUtil.inflate<FragmentAddWorkBinding>(inflater, R.layout.fragment_add_work, container, false)
        binding = AutoClearedValue(this, b)

        setHasOptionsMenu(true)


        presenter.bindView(this)
        val eventBundle = Bundle()
        if ( savedInstanceState != null ) {
            eventBundle.putAll(savedInstanceState)
        }
        eventBundle.putAll(arguments)
        lifeCycleEvents.onNext(EventData(eventBundle, MainView.EVENT_CREATED))



        return b.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
        menuInflater!!.inflate(R.menu.menu_add_work, menu)
        okMenu = menu!!.findItem(R.id.action_ok)
        cancelMenu = menu.findItem(R.id.action_cancel)
        super.onCreateOptionsMenu(menu, menuInflater)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.action_ok -> {
                okClickEvent.onNext("")
                true
            }
            R.id.action_cancel ->{
                cancelClickEvent.onNext("")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        lifeCycleEvents.onNext(EventData(null, MainView.EVENT_DESTROYED))
        super.onDestroyView()
    }

    override fun okClickObservable(): Observable<Any> {
        return okClickEvent
    }

    override fun cancelClickObservable(): Observable<Any> {
        return cancelClickEvent
    }

    override fun dateClickObservable(): Observable<Any> {
        return RxView.clicks(binding.get()!!.txtDate)
    }
    override fun getDate(): String {
        return binding.get()!!.txtDate.text.toString()
    }


    override fun getDescription(): String {
        return binding.get()!!.txtDescription.text.toString()
    }

    override fun getHours(): String {
        return binding.get()!!.txtHours.text.toString()
    }

    override fun viewEvent(): Observable<EventData> {
        return lifeCycleEvents
    }

    override fun getActivityText(): String {
        return binding.get()!!.txtActivity.text.toString()
    }
    override fun setProjectName(projectName: String) {
        binding.get()!!.txtProject.setText( projectName )
    }
    override fun printDatePicker(title:String) {

        datePikerDialog.show(fragmentManager, title)


    }

    override fun datePikedObservable(): Observable<Long> {
        return datePikerDialog.dateSelected
    }
    override fun setDate(date: String) {

        binding.get()!!.txtDate.setText( date )

    }
    override fun setDateError(err: String) {
        if ( err.isEmpty() ){
            binding.get()!!.layoutDate.error = ""
            binding.get()!!.layoutDate.isErrorEnabled = false
        }else{
            binding.get()!!.layoutDate.error = err
            binding.get()!!.layoutDate.isErrorEnabled = true
        }
    }

    override fun setActivityError(err: String) {
        if ( err.isEmpty() ){
            binding.get()!!.layoutActivity.error = ""
            binding.get()!!.layoutActivity.isErrorEnabled = false
        }else{
            binding.get()!!.layoutActivity.error = err
            binding.get()!!.layoutActivity.isErrorEnabled = true
        }    }

    override fun setDescriptionError(err: String) {
        if ( err.isEmpty() ){
            binding.get()!!.layoutDescription.error = ""
            binding.get()!!.layoutDescription.isErrorEnabled = false
        }else{
            binding.get()!!.layoutDescription.error = err
            binding.get()!!.layoutDescription.isErrorEnabled = true
        }    }

    override fun setHoursError(err: String) {
        if ( err.isEmpty() ){
            binding.get()!!.layoutHours.error = ""
            binding.get()!!.layoutHours.isErrorEnabled = false
        }else{
            binding.get()!!.layoutHours.error = err
            binding.get()!!.layoutHours.isErrorEnabled = true
        }    }

}
