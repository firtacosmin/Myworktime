package com.fcc.myworktime.di.modules

import android.app.Application
import android.content.Context
import com.fcc.myworktime.di.AppScope
import dagger.Module
import dagger.Provides

/**
 * Created by firta on 8/31/2017.
 * The module containing the application level modules
 */

@Module(includes = arrayOf(
        FirebaseModule::class,
        UserDataModule::class))
class AppModule {

    @AppScope
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }
}