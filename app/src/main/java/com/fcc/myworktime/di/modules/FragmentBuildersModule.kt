package com.fcc.myworktime.di.modules

import com.fcc.myworktime.MainActivityFragment
import com.fcc.myworktime.ui.login.LoginFragment
import com.fcc.myworktime.ui.projects.ProjectsFragment
import com.fcc.myworktime.ui.registration.RegistrationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by firta on 8/31/2017.
 */

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    internal abstract fun contributeRegistrationFragment(): RegistrationFragment


    @ContributesAndroidInjector
    internal abstract fun contributeProjectsFragment():ProjectsFragment

    @ContributesAndroidInjector
    internal abstract fun contributeTestFragment(): MainActivityFragment
}

