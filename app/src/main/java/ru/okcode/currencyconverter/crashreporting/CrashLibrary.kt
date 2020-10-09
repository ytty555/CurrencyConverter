package ru.okcode.currencyconverter.crashreporting

class CrashLibrary private constructor() {
    companion object {
        fun log(priority: Int, tag: String?, message: String) {
            // TODO add log message
        }

        fun logWarning(t: Throwable) {
            // TODO report non-fatal warning
        }

        fun logError(t: Throwable) {
            // TODO report non-fatal error
        }
    }
}