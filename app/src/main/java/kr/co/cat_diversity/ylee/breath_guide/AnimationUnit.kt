package kr.co.cat_diversity.ylee.breath_guide

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

object AnimationUnit {
    val propScaleX = "scaleX"
    val propScaleY = "scaleY"
    val propAlpha = "alpha"

    fun getAnimator(
        view : View,
        property : String,
        start : Float, end : Float, duration : Long) : Animator {

        val animator : Animator = ObjectAnimator.ofFloat(view, property, start, end)
        animator.duration = duration
        return animator
    }
}