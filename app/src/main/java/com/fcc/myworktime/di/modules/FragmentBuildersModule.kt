package com.fcc.myworktime.di.modules

import com.fcc.myworktime.MainActivityFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by firta on 8/31/2017.
 */

@Module
abstract class FragmentBuildersModule {

//    @ContributesAndroidInjector
//    internal abstract fun contributeLoginFragment(): LoginFragment

//    @ContributesAndroidInjector
//    internal abstract fun contributeRegistrationUserFragment(): RegistrationUserFragment



    @ContributesAndroidInjector
    internal abstract fun contributeTestFragment(): MainActivityFragment
}

