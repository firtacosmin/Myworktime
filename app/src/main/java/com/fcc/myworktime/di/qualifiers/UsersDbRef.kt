package com.fcc.myworktime.di.qualifiers

import com.google.firebase.database.DatabaseReference
import javax.inject.Qualifier

/**
 * Created by firta on 8/31/2017.
 *
 * [Qualifier] for the Users [DatabaseReference]
 */

@Qualifier
annotation class UsersDbRef
