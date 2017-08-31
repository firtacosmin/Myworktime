package com.fcc.myworktime.ui.registration

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fcc.myworktime.R
import com.fcc.myworktime.databinding.FragmentRegistrationBinding
import com.fcc.myworktime.ui.utils.LifeCycleOwnerFragment
import com.fcc.myworktime.utils.AutoClearedValue

/**
 * Created by firta on 8/31/2017.
 * The fragment that will display the information for registering a new user
 */
class RegistrationFragment:RegistrationView, LifeCycleOwnerFragment() {

    private lateinit var binding: AutoClearedValue<FragmentRegistrationBinding>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val b = DataBindingUtil.inflate<FragmentRegistrationBinding>(inflater, R.layout.fragment_registration, container, false)
        binding = AutoClearedValue(this, b)

        return b.root

    }
}