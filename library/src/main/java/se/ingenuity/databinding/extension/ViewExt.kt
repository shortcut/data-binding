package se.ingenuity.databinding.extension

import android.view.View
import androidx.annotation.IdRes

fun View.findParentById(@IdRes id: Int): View? {
    var parent = this.parent

    while ((parent as? View)?.id != id) {
        parent = parent.parent
    }

    return if ((parent as? View)?.id == id) {
        parent
    } else {
        null
    }
}