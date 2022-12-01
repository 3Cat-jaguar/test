package kr.co.cat_diversity.ylee.breath_guide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.cat_diversity.ylee.breath_guide.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}