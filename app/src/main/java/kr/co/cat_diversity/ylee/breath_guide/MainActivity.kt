package kr.co.cat_diversity.ylee.breath_guide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.lifecycle.ViewModelProvider
import kr.co.cat_diversity.ylee.breath_guide.BreathUnit.repeatCount
import kr.co.cat_diversity.ylee.breath_guide.BreathUnit.inhalTime
import kr.co.cat_diversity.ylee.breath_guide.BreathUnit.pauseTime
import kr.co.cat_diversity.ylee.breath_guide.BreathUnit.exhalTime

import kr.co.cat_diversity.ylee.breath_guide.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val breathAnimatorSet by lazy { BreathAnimatorSet(binding.animImg, inhalTime, pauseTime, exhalTime) }
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.noticeText.text = getString(R.string.blank)
        binding.noticeTime.text = getString(R.string.blank)
        binding.animImg.setImageResource(R.drawable.circle)
        val breathAnimator = breathAnimatorSet.getBreathAnimatorSet()
        var repeatCount = repeatCount
        breathAnimator.doOnEnd {
            repeatCount--
            if (repeatCount > 0) {
                breathAnimator.start()
            } else {
                binding.startBtn.text = getString(R.string.anim_start)
                return@doOnEnd
            }
        }
        breathAnimator.doOnStart {
            startInhalCountdown()
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

    override fun onResume() {
        super.onResume()
//        viewModel.leftTime.observe(this) {
//
//        }
    }

    private fun startInhalCountdown() {
        var count = inhalTime/1000
        binding.noticeText.text = "천천히 숨을 마십니다"
        object : CountDownTimer(inhalTime, 1000L) {
            override fun onTick(leftTime: Long) {
                binding.noticeTime.text = "" + count
                count--
            }

            override fun onFinish() {
                startPauseCountdown()
            }
        }.start()
    }

    private fun startPauseCountdown() {
        var count = pauseTime/1000
        binding.noticeText.text = "잠시 숨을 멈춥니다"
        object : CountDownTimer(pauseTime, 1000L) {
            override fun onTick(leftTime: Long) {
                binding.noticeTime.text = "" + count
                count--
            }

            override fun onFinish() {
                startExhalCountdown()
            }
        }.start()
    }

    private fun startExhalCountdown() {
        var count = exhalTime/1000
        binding.noticeText.text = "천천히 숨을 내쉽니다"
        object : CountDownTimer(exhalTime, 1000L) {
            override fun onTick(leftTime: Long) {
                binding.noticeTime.text = "" + count
                count--
            }

            override fun onFinish() {
                binding.noticeText.text = ""
                binding.noticeTime.text = ""
            }
        }.start()
    }
}