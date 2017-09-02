package com.fcc.myworktime.ui.navigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.fcc.myworktime.MainActivity
import com.fcc.myworktime.R
import com.fcc.myworktime.ui.addwork.AddWorkFragment
import com.fcc.myworktime.ui.login.LoginFragment
import com.fcc.myworktime.ui.projectdetails.DetailsFragment
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

    fun openProjectDetails(data: Bundle) {

        val f = DetailsFragment.getInstance(data)
        openFragment(f, true)

    }
    /**
     * will pop the fragment back stack
     * If there are no elements in the backstack then will return false
     * if something can be popped then it will be popped and will return true
     */
    fun onBackPressed():Boolean {

        if ( fManager.backStackEntryCount > 0 ) {
            popBackStack()
            return true
        }
        return false

    }

    private fun popBackStack() {
        fManager.popBackStack()
    }

    private fun openFragment(f:Fragment, addToBackStack:Boolean = false){
        val transaction = fManager.beginTransaction()
                .replace(destinationID,f)
        if ( addToBackStack ){
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun openAddWorkFragment(data: Bundle) {
        val f = AddWorkFragment.getInstance(data)
        openFragment(f, true)
    }


}