package com.nekodev.rocketbrowser

import android.os.Bundle

interface BaseContract {

    interface Presenter<T> {
        fun onStateRestored(savedInstanceState: Bundle)
        fun subscribe(view: T)
        fun onSaveInstanceState(savedInstanceState: Bundle)
        fun unsubscribe()
    }
}