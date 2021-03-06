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
import com.fcc.myworktime.utils.Consts
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * A class that will be used to navigate between screens
 */
class Navigator @Inject constructor(act: MainActivity){


    val destinationID = R.id.main_frame

    var fManager:FragmentManager = act.supportFragmentManager

    val projectsListTransaction = "projectsListTransaction"
    val loginTransaction = "loginTransaction"


    fun goToLogin(){

        clearBackStack(projectsListTransaction)
        openFragment(LoginFragment(), true, loginTransaction)
    }

    private fun clearBackStack(tag:String) {

//        val count = fManager.backStackEntryCount
//        for ( i in 0..count)
//        {
            fManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//        }
    }

    fun goToProjects(){
        clearBackStack(loginTransaction)
        openFragment(ProjectsFragment(), true, projectsListTransaction)
    }

    fun goToRegistration(){
        openFragment(RegistrationFragment())
    }

    fun openAddProjectDialog(){
        ProjectsAddDialog().show(fManager, "")
    }

    fun openProjectDetails(project:String) {
        val data = Bundle()
        data.putString(Consts.DETAILS_DISPLAYED_PROJECT_ID, project)
        val f = DetailsFragment.getInstance(data)
        openFragment(f, true)

    }
    /**
     * will pop the fragment back stack
     * If there are no elements in the backstack then will return false
     * if something can be popped then it will be popped and will return true
     */
    fun onBackPressed():Boolean {

        if ( fManager.backStackEntryCount > 1 ) {
            popBackStack()
            return true
        }
        return false

    }

    private fun popBackStack() {
        fManager.popBackStack()
    }

    private fun openFragment(f:Fragment, addToBackStack:Boolean = false, backstackTag:String? = null){
        val transaction = fManager.beginTransaction()
                .replace(destinationID,f)
        if ( addToBackStack ){
            transaction.addToBackStack(backstackTag)
        }
        transaction.commit()
    }

    fun openAddWorkFragment(data: Bundle) {
        val f = AddWorkFragment.getInstance(data)
        openFragment(f, true)
    }


}