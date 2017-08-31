package com.fcc.myworktime.ui.utils

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import com.fcc.myworktime.di.Injectable

/**
 * Created by firta on 9/1/2017.
 *
 */
open class LifeCycleOwnerDialogFragment : DialogFragment() , Injectable, LifecycleRegistryOwner
{
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }
}