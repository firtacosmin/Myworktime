package com.fcc.myworktime.di.modules

import com.fcc.myworktime.data.Auth
import com.fcc.myworktime.data.ProjectsDAO
import com.fcc.myworktime.data.UserDAO
import com.fcc.myworktime.data.UserData
import com.fcc.myworktime.di.AppScope
import dagger.Module
import dagger.Provides

/**
 * Created by firta on 8/31/2017.
 */

@Module
class UserDataModule {

    @Provides
    @AppScope
    fun provideUData(auth: Auth, userDAO: UserDAO, projectsDAO: ProjectsDAO): UserData {
        return UserData(auth, userDAO, projectsDAO)
    }

}
