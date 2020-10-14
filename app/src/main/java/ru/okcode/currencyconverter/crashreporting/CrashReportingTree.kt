package ru.okcode.currencyconverter.crashreporting

import android.util.Log
import timber.log.Timber

class CrashReportingTree: Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        CrashLibrary.log(priority, tag, message)

        if (t != null) {
            if (priority == Log.ERROR) {
                CrashLibrary.logError(t)
            } else if (priority == Log.WARN) {
                CrashLibrary.logWarning(t)
            }
        }
    }
}