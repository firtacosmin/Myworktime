package com.fcc.myworktime.ui.utils

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.support.v4.app.Fragment
import com.fcc.myworktime.di.Injectable

/**
 * Created by firta on 8/31/2017.
 * a [Fragment] that overwrites the [LifecycleRegistryOwner] and [Injectable] interfaces
 * and implements the [getLifecycle] method
 * Will be used by all the [Fragment]s that what to have objects injected into them and need to
 * have a lifecycle ownership
 */
open class LifeCycleOwnerFragment : Fragment() , Injectable, LifecycleRegistryOwner
{
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }
}