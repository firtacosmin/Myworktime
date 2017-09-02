package com.fcc.myworktime.ui.registration

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fcc.myworktime.R
import com.fcc.myworktime.databinding.FragmentRegistrationBinding
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.LifeCycleOwnerFragment
import com.fcc.myworktime.ui.utils.MainView
import com.fcc.myworktime.utils.AutoClearedValue
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * The fragment that will display the information for registering a new user
 */
class RegistrationFragment:RegistrationView, LifeCycleOwnerFragment() {


    private lateinit var binding: AutoClearedValue<FragmentRegistrationBinding>
    private var lifeCycleEvents = PublishSubject.create<EventData>()

    @Inject lateinit var presenter:RegistrationPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val b = DataBindingUtil.inflate<FragmentRegistrationBinding>(inflater, R.layout.fragment_registration, container, false)
        binding = AutoClearedValue(this, b)

        presenter.bindView(this)


        lifeCycleEvents.onNext(EventData(savedInstanceState, MainView.EVENT_CREATED))


        return b.root

    }

    override fun onDestroyView() {
        lifeCycleEvents.onNext(EventData("", MainView.EVENT_DESTROYED))

        super.onDestroyView()
    }

    override fun viewEvent(): Observable<EventData> {
        return lifeCycleEvents
    }

    override fun registerObservable(): Observable<Any> {
        return RxView.clicks(binding.get()!!.btnRegister)
    }

    override fun getEmail(): String {
        return binding.get()!!.txtEmail.text.toString()
    }

    override fun getPassword(): String {
        return binding.get()!!.txtPass.text.toString()
    }

    override fun getRePassword(): String {
        return binding.get()!!.txtRepass.text.toString()
    }

    override fun printEmailError(err: String) {

        binding.get()!!.layoutEmail.error = err
        binding.get()!!.layoutEmail.isErrorEnabled = err.isNotEmpty()
    }
    override fun printGeneralError(err: String) {

        binding.get()!!.txtError.text = err

    }
    override fun printPassError(err: String) {
        binding.get()!!.layoutPass.error = err
        binding.get()!!.layoutPass.isErrorEnabled = err.isNotEmpty()
    }

    override fun printRePassError(err: String) {
        binding.get()!!.layoutRePass.error = err
        binding.get()!!.layoutRePass.isErrorEnabled = err.isNotEmpty()
    }
    override fun hideLoading() {
        binding.get()!!.layoutRePass.visibility = View.VISIBLE
        binding.get()!!.layoutPass.visibility = View.VISIBLE
        binding.get()!!.layoutEmail.visibility = View.VISIBLE
        binding.get()!!.btnRegister.visibility = View.VISIBLE
        binding.get()!!.loading.visibility = View.INVISIBLE
    }

    override fun printLoading() {
        binding.get()!!.layoutRePass.visibility = View.INVISIBLE
        binding.get()!!.layoutPass.visibility = View.INVISIBLE
        binding.get()!!.layoutEmail.visibility = View.INVISIBLE
        binding.get()!!.btnRegister.visibility = View.INVISIBLE
        binding.get()!!.loading.visibility = View.VISIBLE    }



}