package com.fcc.myworktime.ui.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fcc.myworktime.R
import com.fcc.myworktime.databinding.FragmentLoginBinding
import com.fcc.myworktime.ui.utils.LifeCycleOwnerFragment
import com.fcc.myworktime.utils.AutoClearedValue

/**
 * Created by firta on 8/31/2017.
 * The fragment that will implement the [LoginView] for the login functionality
 */
class LoginFragment:LoginView, LifeCycleOwnerFragment() {



    private lateinit var binding:AutoClearedValue<FragmentLoginBinding>


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val b:FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login,container, false)
        binding = AutoClearedValue(this, b)


        return b.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }
}