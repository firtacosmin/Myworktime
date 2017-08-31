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
        val EVENT_PAUSED = "EVENT_PAUSED"
        val EVENT_RESUMED = "EVENT_RESUMED"
        val EVENT_ACTIVITY_ATTACHED = "EVENT_ACTIVITY_ATTACHED"
    }

    fun viewEvent(): Observable<EventData>
}