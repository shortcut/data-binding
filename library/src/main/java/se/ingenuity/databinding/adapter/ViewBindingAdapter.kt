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
    @BindingAdapter(
        value = [
            "visibleOrGone",
            "transition",
            "transitionRoot",
            "transitionIncludeTargets",
            "transitionExcludeTargets"
        ],
        requireAll = false
    )
    fun View.setVisibleOrGone(
        visible: Boolean,
        transition: Transition?,
        @IdRes transitionRoot: Int?,
        includeTargets: List<Int>?,
        excludeTargets: List<Int>?
    ) {
        beginDelayedTransition(transition, transitionRoot, includeTargets, excludeTargets)

        visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            "visibleOrInvisible",
            "transition",
            "transitionRoot",
            "transitionIncludeTargets",
            "transitionExcludeTargets"
        ],
        requireAll = false
    )
    fun View.setVisibleOrInvisible(
        visible: Boolean,
        transition: Transition?,
        @IdRes transitionRoot: Int?,
        includeTargets: List<Int>?,
        excludeTargets: List<Int>?
    ) {
        beginDelayedTransition(transition, transitionRoot, includeTargets, excludeTargets)

        visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    private fun View.beginDelayedTransition(
        transition: Transition?,
        @IdRes transitionRoot: Int?,
        includeTargets: List<Int>?,
        excludeTargets: List<Int>?
    ) {
        if (transition != null) {
            val root = if (transitionRoot != null) {
                findParentById(transitionRoot)
            } else {
                parent as View
            } ?: parent as View

            includeTargets?.forEach { targetId ->
                transition.addTarget(targetId)
            }

            excludeTargets?.forEach { targetId ->
                transition.excludeTarget(targetId, true)
            }

            TransitionManager.beginDelayedTransition(root as ViewGroup, transition)
        }
    }
}
