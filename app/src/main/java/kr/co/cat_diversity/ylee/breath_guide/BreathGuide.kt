package kr.co.cat_diversity.ylee.breath_guide

import android.app.Application
import android.content.Context
import timber.log.Timber

class BreathGuide : Application() {
    init {
        instance = this
    }

    companion object{
        lateinit var instance : BreathGuide
        fun getApplicationContext() : Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}