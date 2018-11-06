package com.nekodev.rocketbrowser.util

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive

fun CoroutineScope.log(message: String) {
    Log.d("RocketBrowserLog", "[${Thread.currentThread()} \t ${this.coroutineContext}] $message")
}
