package com.fcc.myworktime.ui.navigation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.fcc.myworktime.MainActivity
import com.fcc.myworktime.R
import com.fcc.myworktime.ui.login.LoginFragment
import com.fcc.myworktime.ui.projects.ProjectsFragment
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * A class that will be used to navigate between screens
 */
class Navigator @Inject constructor(){

    val destinationID = R.id.main_frame

    lateinit var fManager:FragmentManager

    fun bindWithActivity(act:MainActivity){
        fManager = act.supportFragmentManager
    }

    fun goToLogin(){
        openFragment(LoginFragment())
    }

    fun goToProjects(){
        openFragment(ProjectsFragment())
    }


    private fun openFragment(f:Fragment){
        fManager.beginTransaction()
                .replace(destinationID,f)
                .commit()
    }

}