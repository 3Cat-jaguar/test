package kr.co.cat_diversity.ylee.breath_guide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.animation.doOnEnd

import kr.co.cat_diversity.ylee.breath_guide.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val breathAnimatorSet by lazy { BreathAnimatorSet(binding.animImg) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.noticeText.text = getString(R.string.blank)
        binding.noticeTime.text = getString(R.string.blank)
        binding.animImg.setImageResource(R.drawable.circle)
        val breathAnimator = breathAnimatorSet.getBreathAnimatorSet()
        var repeatCount = breathAnimatorSet.repeatCount
        breathAnimator.doOnEnd {
            repeatCount--
            if (repeatCount > 0) {
                breathAnimator.start()
            } else {
                binding.startBtn.text = getString(R.string.anim_start)
                return@doOnEnd
            }
        }
        binding.startBtn.setOnClickListener {
            if ((it as Button).text == getString(R.string.anim_start)){
                binding.startBtn.text = getString(R.string.anim_proceed)
                breathAnimator.start()
            } else {
                return@setOnClickListener
            }
        }

        setContentView(binding.root)
    }

}