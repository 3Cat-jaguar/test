package kr.co.cat_diversity.ylee.breath_guide

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.core.animation.doOnRepeat
import kr.co.cat_diversity.ylee.breath_guide.databinding.ActivityMainBinding

const val inhalTime = 6000L
const val pauseTime = 3000L
const val exhalTime = 6000L

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.noticeText.text = ""
        binding.noticeTime.text = ""
        binding.animImg.setImageResource(R.drawable.circle)
        binding.startBtn.setOnClickListener {
            getImageAnimatorSet().start()
        }

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun getImageAnimatorSet() : AnimatorSet {
        // animatorSet 에서 동작할 animation 셋팅
        val animatorSet = AnimatorSet()
        val inhalAnimator1 = getAnimator("scaleX", 0.5f, 2f, inhalTime)
        val inhalAnimator2 = getAnimator("scaleY", 0.5f, 2f, inhalTime)
        val inhalAnimator3 = getAnimator("alpha", 1f, 0.3f, inhalTime)

        val pauseAnimator1 = getAnimator("scaleX", 2f, 2f, pauseTime)
        val pauseAnimator2 = getAnimator("scaleY", 2f, 2f, pauseTime)
        val pauseAnimator3 = getAnimator("alpha", 0.3f, 0.3f, pauseTime)

        val exhalAnimator1 = getAnimator("scaleX", 2f, 0.5f, exhalTime)
        val exhalAnimator2 = getAnimator("scaleY", 2f, 0.5f, exhalTime)
        val exhalAnimator3 = getAnimator("alpha", 0.3f, 1f, exhalTime)

        animatorSet.play(inhalAnimator1)
            .with(inhalAnimator2)
            .with(inhalAnimator3)
            .before(pauseAnimator1)
            .before(exhalAnimator1)

        animatorSet.play(pauseAnimator1)
            .with(pauseAnimator2)
            .with(pauseAnimator3)
            .before(exhalAnimator1)
            .after(inhalAnimator3)

        animatorSet.play(exhalAnimator1)
            .with(exhalAnimator2)
            .with(exhalAnimator3)
            .after(inhalAnimator3)
            .after(pauseAnimator3)



        return animatorSet
    }

//    private fun getInhalAnimator() : AnimatorSet.Builder {
//        val inhalAnimatorSet =
//            animatorSet.play(getAnimator(animImg, "scaleX", 10f, 100f, 3000))
//                .with(getAnimator(animImg, "scaleY", 10f, 100f, 3000))
//                .with(getAnimator(animImg, "alpha", 1f, 0.3f, 3000))
//        return inhalAnimatorSet
//    }

    private fun getAnimator(
        property : String,
        start : Float, end : Float, duration : Long) : Animator {

        val view = binding.animImg
        val animator : Animator = ObjectAnimator.ofFloat(view, property, start, end)
        animator.duration = duration
        return animator
    }
}