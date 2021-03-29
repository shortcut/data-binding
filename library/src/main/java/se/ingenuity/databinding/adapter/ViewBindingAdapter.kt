package se.ingenuity.databinding.adapter

import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import androidx.transition.TransitionInflater
import se.ingenuity.databinding.extension.findParentById

private typealias TransitionX = androidx.transition.Transition
private typealias TransitionManagerX = androidx.transition.TransitionManager

object ViewBindingAdapter {
    @JvmStatic
    @BindingAdapter(
        value = [
            "isVisible",
            "transition",
            "transitionRoot",
            "transitionIncludeTargets",
            "transitionExcludeTargets"
        ],
        requireAll = false
    )
    fun View.setVisibleOrGone(
        isVisible: Boolean,
        transition: Any?,
        @IdRes transitionRoot: Int?,
        includeTargets: Any?,
        excludeTargets: Any?
    ) {
        beginDelayedTransition(transition, transitionRoot, includeTargets, excludeTargets)

        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            "isInvisible",
            "transition",
            "transitionRoot",
            "transitionIncludeTargets",
            "transitionExcludeTargets"
        ],
        requireAll = false
    )
    fun View.setVisibleOrInvisible(
        isInvisible: Boolean,
        transition: Any?,
        @IdRes transitionRoot: Int?,
        includeTargets: Any?,
        excludeTargets: Any?
    ) {
        beginDelayedTransition(transition, transitionRoot, includeTargets, excludeTargets)

        visibility = if (isInvisible) View.INVISIBLE else View.VISIBLE
    }

    private fun View.beginDelayedTransition(
        transition: Any?,
        @IdRes transitionRoot: Int?,
        includeTarget: Any?,
        excludeTarget: Any?
    ) {
        if (transition != null &&
            (transition is Transition || transition is TransitionX ||
                    (transition is Int && transition != 0))
        ) {
            val root = if (transitionRoot != null) {
                findParentById(transitionRoot)
            } else {
                parent as View
            } ?: parent as View

            val inflatedTransition = (transition as? Int)?.let { resource ->
                TransitionInflater.from(this.context).inflateTransition(resource)
            } ?: transition

            includeTarget(inflatedTransition, includeTarget, 0)
            excludeTarget(inflatedTransition, excludeTarget, 0)

            if (inflatedTransition is Transition) {
                TransitionManager.beginDelayedTransition(root as ViewGroup, inflatedTransition)
            } else if (inflatedTransition is TransitionX) {
                TransitionManagerX.beginDelayedTransition(root as ViewGroup, inflatedTransition)
            }

        }
    }

    private fun includeTarget(transition: Any, target: Any?, recursionLevel: Int) {
        if (recursionLevel > 1) {
            return
        }
        when (target) {
            is Class<*> -> (transition as? Transition)?.addTarget(target)
                ?: (transition as? TransitionX)?.addTarget(target)
            is Int -> (transition as? Transition)?.addTarget(target)
                ?: (transition as? TransitionX)?.addTarget(target)
            is String -> (transition as? Transition)?.addTarget(target)
                ?: (transition as? TransitionX)?.addTarget(target)
            is View -> (transition as? Transition)?.addTarget(target)
                ?: (transition as? TransitionX)?.addTarget(target)
            is Collection<*> -> {
                if (recursionLevel > 0) {
                    return
                }

                target.forEach {
                    includeTarget(transition, it, recursionLevel + 1)
                }
            }
        }
    }

    private fun excludeTarget(transition: Any, target: Any?, recursionLevel: Int) {
        when (target) {
            is Class<*> -> (transition as? Transition)?.excludeTarget(target, true)
                ?: (transition as? TransitionX)?.excludeTarget(target, true)
            is Int -> (transition as? Transition)?.excludeTarget(target, true)
                ?: (transition as? TransitionX)?.excludeTarget(target, true)
            is String -> (transition as? Transition)?.excludeTarget(target, true)
                ?: (transition as? TransitionX)?.excludeTarget(target, true)
            is View -> (transition as? Transition)?.excludeTarget(target, true)
                ?: (transition as? TransitionX)?.excludeTarget(target, true)
            is Collection<*> -> {
                if (recursionLevel > 0) {
                    return
                }

                target.forEach {
                    excludeTarget(transition, it, recursionLevel + 1)
                }
            }
        }
    }
}
