package kr.co.cat_diversity.ylee.breath_guide

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _curBreath : MutableLiveData<Int> = MutableLiveData()
    val curBreath : LiveData<Int> = _curBreath
    private val _curSet : MutableLiveData<Int> = MutableLiveData()
    val curSet : LiveData<Int> = _curSet

    fun addCurBreath() {
        val cur = _curBreath.value?:1
        _curBreath.value = cur + 1
    }

    fun setCurBreath(cur : Int) {
        _curBreath.value = cur
    }

    fun addCurSet() {
        val cur = _curSet.value?:1
        _curSet.value = cur + 1
    }

    fun setCurSet(cur : Int) {
        _curSet.value = cur
    }
}