package com.gca.mygca.utils

import android.content.res.Resources
import android.widget.EditText

fun dpToPx(dp: Int): Int {
    val metrics = Resources.getSystem().displayMetrics
    return dp * (metrics.densityDpi / 160)
}

