package com.fcc.myworktime.ui.registration

import com.fcc.myworktime.data.Auth
import com.fcc.myworktime.data.UserDAO
import com.fcc.myworktime.data.UserData
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * The model that will offer the data manipulation processes for the [RegistrationPresenter]
 */
class RegistrationModel @Inject constructor(
        var userDAO: UserDAO,
        var auth: Auth,
        var uData:UserData
){


}