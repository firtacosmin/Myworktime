package com.fcc.myworktime.di.modules

import com.fcc.myworktime.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by firta on 8/31/2017.
 * the module that will inject objects into the MainActivity and into the fragments
 */
@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
    internal abstract fun contributeMainActivity(): MainActivity
}