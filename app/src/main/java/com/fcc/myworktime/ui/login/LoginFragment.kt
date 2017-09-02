package com.fcc.myworktime.ui.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fcc.myworktime.R
import com.fcc.myworktime.databinding.FragmentLoginBinding
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
 * The fragment that will implement the [LoginView] for the login functionality
 */
class LoginFragment:LoginView, LifeCycleOwnerFragment() {
    private var lifeCycleEvents = PublishSubject.create<EventData>()


    private lateinit var binding:AutoClearedValue<FragmentLoginBinding>
    @Inject lateinit var presenter:LoginPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val b:FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login,container, false)
        binding = AutoClearedValue(this, b)

        presenter.bindView(this)


        lifeCycleEvents.onNext(EventData(savedInstanceState, MainView.EVENT_CREATED))


        return b.root
    }

    override fun onDestroyView() {

        lifeCycleEvents.onNext(EventData(null, MainView.EVENT_DESTROYED))
        super.onDestroyView()
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }

    override fun observerLoginClick(): Observable<Any> {
        return RxView.clicks(binding.get()!!.btnLogin)
    }


    override fun observerRegisterClick(): Observable<Any> {
        return RxView.clicks(binding.get()!!.txtRegister)
    }

    override fun viewEvent(): Observable<EventData> {
        return  lifeCycleEvents
    }
    override fun getEmail(): String {
        return binding.get()!!.txtUsername.text.toString()
    }

    override fun getPassword(): String {
        return binding.get()!!.txtPass.text.toString()
    }

    override fun printError(loginErrorMessage: String?) {
        hideLoading()
        binding.get()!!.txtError.text = loginErrorMessage
    }

    override fun printPassError(s: String) {

        binding.get()!!.layoutPassword.isErrorEnabled = true
        binding.get()!!.layoutPassword.error = s
    }

    override fun printEmailError(s: String) {

        binding.get()!!.layoutUsername.isErrorEnabled = true
        binding.get()!!.layoutUsername.error = s    }

    override fun printLoading() {
        binding.get()!!.layoutUsername.visibility = View.INVISIBLE
        binding.get()!!.layoutPassword.visibility = View.INVISIBLE
        binding.get()!!.txtError.visibility = View.INVISIBLE
        binding.get()!!.txtRegister.visibility = View.INVISIBLE
        binding.get()!!.loading.visibility = View.VISIBLE
        binding.get()!!.btnLogin.visibility = View.INVISIBLE}

    private fun hideLoading() {
        binding.get()!!.layoutUsername.visibility = View.VISIBLE
        binding.get()!!.layoutPassword.visibility = View.VISIBLE
        binding.get()!!.txtError.visibility = View.VISIBLE
        binding.get()!!.txtRegister.visibility = View.VISIBLE
        binding.get()!!.loading.visibility = View.INVISIBLE
        binding.get()!!.btnLogin.visibility = View.VISIBLE
    }
}