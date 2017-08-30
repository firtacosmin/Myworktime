package com.fcc.myworktime.di.modules

import com.fcc.myworktime.di.AppScope
import com.fcc.myworktime.di.qualifiers.ProjectsDbRef
import com.fcc.myworktime.di.qualifiers.UsersDbRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides

/**
 * Created by firta on 8/31/2017.
 * The module that will provide the necessary references for accessing the Firebase features
 */

@Module
class FirebaseModule {


    @Provides
    @AppScope
    fun provideAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @AppScope
    fun provideDatabase(): FirebaseDatabase {
        val instance = FirebaseDatabase.getInstance()
        instance.setPersistenceEnabled(true)
        return instance
    }


    @Provides
    @AppScope
    @UsersDbRef
    fun provideUsresDbRef(db: FirebaseDatabase): DatabaseReference {
        return db.getReference("users")
    }

    @Provides
    @AppScope
    @ProjectsDbRef
    fun provideSchedulerDbRef(db: FirebaseDatabase): DatabaseReference {
        return db.getReference("projects")
    }

}
