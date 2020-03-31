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
        includeTargets: Any?,
        excludeTargets: Any?
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
        includeTargets: Any?,
        excludeTargets: Any?
    ) {
        beginDelayedTransition(transition, transitionRoot, includeTargets, excludeTargets)

        visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    private fun View.beginDelayedTransition(
        transition: Transition?,
        @IdRes transitionRoot: Int?,
        includeTarget: Any?,
        excludeTarget: Any?
    ) {
        if (transition != null) {
            val root = if (transitionRoot != null) {
                findParentById(transitionRoot)
            } else {
                parent as View
            } ?: parent as View

            transition.includeTarget(includeTarget, 0)
            transition.excludeTarget(excludeTarget, 0)

            TransitionManager.beginDelayedTransition(root as ViewGroup, transition)
        }
    }

    private fun Transition.includeTarget(target: Any?, recursionLevel: Int) {
        if (recursionLevel > 1) {
            return
        }
        when (target) {
            is Class<*> -> addTarget(target)
            is Int -> addTarget(target)
            is String -> addTarget(target)
            is View -> addTarget(target)
            is Collection<*> -> {
                if (recursionLevel > 0) {
                    return
                }

                target.forEach {
                    includeTarget(it, recursionLevel + 1)
                }
            }
        }
    }

    private fun Transition.excludeTarget(target: Any?, recursionLevel: Int) {
        when (target) {
            is Class<*> -> excludeTarget(target, true)
            is Int -> excludeTarget(target, true)
            is String -> excludeTarget(target, true)
            is View -> excludeTarget(target, true)
            is Collection<*> -> {
                if (recursionLevel > 0) {
                    return
                }

                target.forEach {
                    excludeTarget(it, recursionLevel + 1)
                }
            }
        }
    }
}
