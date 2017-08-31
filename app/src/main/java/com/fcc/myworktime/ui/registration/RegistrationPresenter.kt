package com.fcc.myworktime.ui.registration

import com.fcc.myworktime.ui.navigation.Navigator
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * The presenter for the [RegistrationView]
 */
class RegistrationPresenter @Inject constructor(
        var navigator:Navigator,
        var model:RegistrationModel
){


    private lateinit var view:RegistrationView

    fun bindView(v:RegistrationView){
        view = v


    }



}