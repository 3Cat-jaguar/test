package kr.co.cat_diversity.ylee.breath_guide

import android.animation.AnimatorSet
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
import kr.co.cat_diversity.ylee.breath_guide.BreathUnit.totalSet
import kr.co.cat_diversity.ylee.breath_guide.BreathUnit.waitTime

import kr.co.cat_diversity.ylee.breath_guide.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var breathAnimator : AnimatorSet
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.noticeText.text = getString(R.string.blank)
        binding.noticeTime.text = getString(R.string.blank)
        setNoticeBreath(0)
        setNoticeSet(0)
        binding.animImg.setImageResource(R.drawable.circle)

        breathAnimator = BreathAnimatorSet(binding.animImg, inhalTime, pauseTime, exhalTime).getBreathAnimatorSet()
        breathAnimator.doOnEnd {
            viewModel.addCurBreath()
        }
        breathAnimator.doOnStart {
            startInhalCountdown()
        }

        binding.startBtn.setOnClickListener {
            if ((it as Button).text == getString(R.string.anim_start)){
                binding.startBtn.text = getString(R.string.anim_proceed)
                viewModel.setCurSet(1)
            } else {
                return@setOnClickListener
            }
        }

        viewModel.curBreath.observe(this) {
            Timber.d("curBreath observed $it")
            setNoticeBreath(it)
            if (it <= repeatCount) {
                breathAnimator.start()
            } else {
                setNoticeBreath(repeatCount)
                viewModel.addCurSet()
            }
        }

        viewModel.curSet.observe(this) {
            Timber.d("curSet observed $it")
            setNoticeSet(it)
            when (it) {
                1 -> viewModel.setCurBreath(1)
                in 2..totalSet -> startWaitCountdown(viewModel.curSet.value?:1)
                else -> {
                    binding.startBtn.text = getString(R.string.anim_start)
                    binding.noticeText.text = "모두 종료되었습니다."
                    binding.noticeTime.text = ""
                    setNoticeBreath(0)
                    setNoticeSet(0)
                }
            }
        }

        setContentView(binding.root)
    }

    private fun setNoticeBreath(curBreath : Int) {
        binding.noticeBreath.text = "$curBreath ${getString(R.string.breath_count)} / $repeatCount ${getString(R.string.breath_count)}"
    }

    private fun setNoticeSet(curSet: Int) {
        binding.noticeSet.text = "$curSet ${getString(R.string.breath_set)} / $totalSet ${getString(R.string.breath_set)}"
    }

    private fun startWaitCountdown(curSet : Int) {
        if (curSet == totalSet) {
            Timber.d("$curSet / $totalSet")
        }
        var count = waitTime/1000
        binding.noticeText.text = "잠시 휴식합니다"
        object : CountDownTimer(waitTime, 1000L) {
            override fun onTick(leftTime: Long) {
                binding.noticeTime.text = "" + count
                count--
            }

            override fun onFinish() {
                viewModel.setCurBreath(1)
            }
        }.start()
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
                Timber.d("호흡끝")
            }
        }.start()
    }
}