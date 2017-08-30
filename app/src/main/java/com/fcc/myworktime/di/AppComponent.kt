package com.fcc.myworktime.di

import android.app.Application
import com.fcc.myworktime.MainApp
import com.fcc.myworktime.di.modules.ActivityBuilderModule
import com.fcc.myworktime.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

/**
 * Created by firta on 8/31/2017.
 * The [Component] for the Application
 */

@AppScope
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuilderModule::class))
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(mainApp: MainApp)
}
