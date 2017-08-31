package com.fcc.myworktime.ui.utils

import io.reactivex.Observable

/**
 * Created by firta on 8/31/2017.
 *
 */
interface MainView {
    companion object {
        val EVENT_CREATED = "EVENT_CREATED"
        val EVENT_DESTROYED = "EVENT_DESTROYED"
    }

    fun viewEvent(): Observable<EventData>
}