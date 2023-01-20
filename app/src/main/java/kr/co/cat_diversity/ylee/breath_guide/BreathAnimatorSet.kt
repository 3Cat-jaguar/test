package kr.co.cat_diversity.ylee.breath_guide

import android.animation.AnimatorSet
import android.view.View
import kr.co.cat_diversity.ylee.breath_guide.AnimationUnit.getAnimator
import kr.co.cat_diversity.ylee.breath_guide.AnimationUnit.propScaleX
import kr.co.cat_diversity.ylee.breath_guide.AnimationUnit.propScaleY
import kr.co.cat_diversity.ylee.breath_guide.AnimationUnit.propAlpha

class BreathAnimatorSet(view : View) {

    val repeatCount = 3
    val inhalTime = 6000L
    val pauseTime = 3000L
    val exhalTime = 6000L
    val minSize = 0.5f
    val maxSize = 2f
    val minAlpha = 0.3f
    val maxAlpha = 1f

    val inhalAnimator1 = getAnimator(view, propScaleX, minSize, maxSize, inhalTime)
    val inhalAnimator2 = getAnimator(view, propScaleY, minSize, maxSize, inhalTime)
    val inhalAnimator3 = getAnimator(view, propAlpha, maxAlpha, minAlpha, inhalTime)

    val pauseAnimator1 = getAnimator(view, propScaleX, maxSize, maxSize, pauseTime)
    val pauseAnimator2 = getAnimator(view, propScaleY, maxSize, maxSize, pauseTime)
    val pauseAnimator3 = getAnimator(view, propAlpha, minAlpha, minAlpha, pauseTime)

    val exhalAnimator1 = getAnimator(view, propScaleX, maxSize, minSize, exhalTime)
    val exhalAnimator2 = getAnimator(view, propScaleY, maxSize, minSize, exhalTime)
    val exhalAnimator3 = getAnimator(view, propAlpha, minAlpha, maxAlpha, exhalTime)


    fun getBreathAnimatorSet() : AnimatorSet {
        // animatorSet 에서 동작할 animation 셋팅
        val animatorSet = AnimatorSet()

        // 흡기 상태 애니메이션
        animatorSet.play(inhalAnimator1)
            .with(inhalAnimator2)
            .with(inhalAnimator3)
            .before(pauseAnimator1)
            .before(exhalAnimator1)

        // 호흡 멈춤 상태 애니메이션
        animatorSet.play(pauseAnimator1)
            .with(pauseAnimator2)
            .with(pauseAnimator3)
            .before(exhalAnimator1)
            .after(inhalAnimator3)

        // 호기 상태 애니메이션
        animatorSet.play(exhalAnimator1)
            .with(exhalAnimator2)
            .with(exhalAnimator3)
            .after(inhalAnimator3)
            .after(pauseAnimator3)

        return animatorSet
    }

}