package com.nekodev.rocketbrowser

import android.arch.lifecycle.LifecycleObserver
import android.os.Bundle

interface BaseContract {

    interface ScopedPresenter<T> : Presenter<T> {
        val scope: LifecycleObserver
    }

    interface Presenter<T> {
        fun onStateRestored(savedInstanceState: Bundle)
        fun subscribe(view: T)
        fun onSaveInstanceState(savedInstanceState: Bundle)
        fun unsubscribe()
    }
}