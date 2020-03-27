package se.ingenuity.databinding.adapter

import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import se.ingenuity.databinding.extension.findParentById

object ViewBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["visibleOrGone", "transition", "sceneRoot"], requireAll = false)
    fun View.setVisibleOrGone(
        visible: Boolean,
        transition: Transition?,
        @IdRes sceneRoot: Int?
    ) {
        beginDelayedTransition(transition, sceneRoot)

        visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(value = ["visibleOrInvisible", "transition", "sceneRoot"], requireAll = false)
    fun View.setVisibleOrInvisible(
        visible: Boolean,
        transition: Transition?,
        @IdRes sceneRoot: Int?
    ) {
        beginDelayedTransition(transition, sceneRoot)

        visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    private fun View.beginDelayedTransition(
        transition: Transition?,
        @IdRes sceneRoot: Int?
    ) {
        if (transition != null) {
            val root = if (sceneRoot != null) {
                findParentById(sceneRoot)
            } else {
                parent as View
            } ?: parent as View

            TransitionManager.beginDelayedTransition(root as ViewGroup, transition)
        }
    }
}
