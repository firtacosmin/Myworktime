package com.fcc.myworktime.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by firta on 8/31/2017.
 * A class used to keep information only on the lifetime duration of a fragment
 */
class AutoClearedValue<T>(fragment: Fragment, private var value: T?) {

    init {
        val fragmentManager = fragment.fragmentManager
        fragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentViewDestroyed(fm: FragmentManager?, f: Fragment?) {
                        if (f === fragment) {
                            this@AutoClearedValue.value = null
                            fragmentManager.unregisterFragmentLifecycleCallbacks(this)
                        }
                    }
                }, false)
    }

    fun get(): T? {
        return value
    }
}
