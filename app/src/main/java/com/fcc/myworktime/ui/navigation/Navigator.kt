package com.fcc.myworktime.ui.navigation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.fcc.myworktime.MainActivity
import com.fcc.myworktime.R
import com.fcc.myworktime.ui.login.LoginFragment
import com.fcc.myworktime.ui.projects.ProjectsFragment
import com.fcc.myworktime.ui.projects.addproject.ProjectsAddDialog
import com.fcc.myworktime.ui.registration.RegistrationFragment
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * A class that will be used to navigate between screens
 */
class Navigator @Inject constructor(act: MainActivity){


    val destinationID = R.id.main_frame

    var fManager:FragmentManager = act.supportFragmentManager

    fun goToLogin(){
        openFragment(LoginFragment())
    }

    fun goToProjects(){
        openFragment(ProjectsFragment())
    }

    fun goToRegistration(){
        openFragment(RegistrationFragment())
    }

    fun openAddProjectDialog(){
        ProjectsAddDialog().show(fManager, "")
    }


    private fun openFragment(f:Fragment){
        fManager.beginTransaction()
                .replace(destinationID,f)
                .commit()
    }

}