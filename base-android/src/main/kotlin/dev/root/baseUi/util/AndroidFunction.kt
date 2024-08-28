package dev.root.baseUi.util

import android.content.pm.PackageManager
import android.os.Looper
import androidx.annotation.RestrictTo

@get:RestrictTo(RestrictTo.Scope.LIBRARY)
internal inline val isMainThread: Boolean
    get() = Looper.myLooper() == Looper.getMainLooper()

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal fun checkIsMainThread() = check(isMainThread)

fun isAllPermissionsGranted(grantResults: IntArray): Boolean {
    var isGranted = true

    for (grantResult in grantResults) {
        isGranted = grantResult == PackageManager.PERMISSION_GRANTED

        if (!isGranted) {
            break
        }
    }

    return isGranted
}
