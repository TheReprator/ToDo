package dev.root.baseUi.util

import android.os.Build

val isAndroidPOrLater
    get() = Build.VERSION_CODES.P <= Build.VERSION.SDK_INT
val isAndroidOOrLater
    get() = Build.VERSION_CODES.O <= Build.VERSION.SDK_INT
val isAndroidMOrLater
    get() = Build.VERSION_CODES.M <= Build.VERSION.SDK_INT
val isAndroidNOrLater
    get() = Build.VERSION_CODES.N <= Build.VERSION.SDK_INT
