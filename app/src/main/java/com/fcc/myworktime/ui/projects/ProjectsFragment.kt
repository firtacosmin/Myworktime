package com.fcc.myworktime.ui.projects

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fcc.myworktime.R
import com.fcc.myworktime.databinding.FragmentLoginBinding
import com.fcc.myworktime.databinding.FragmentProjectsBinding
import com.fcc.myworktime.ui.utils.LifeCycleOwnerFragment
import com.fcc.myworktime.utils.AutoClearedValue

/**
 * Created by firta on 8/31/2017.
 * The fragment that will implement the [ProjectsView]
 */
class ProjectsFragment: LifeCycleOwnerFragment(), ProjectsView {
    private lateinit var binding: AutoClearedValue<FragmentProjectsBinding>


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val b: FragmentProjectsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_projects,container, false)
        binding = AutoClearedValue(this, b)


        return b.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }
}