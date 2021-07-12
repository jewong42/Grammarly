package com.jewong.myapplication.ui.main

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val timerCount = 3
    val timers: MutableLiveData<List<Timer>> = MutableLiveData()
    val list = mutableListOf<Timer>()
    val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable = object : Runnable {
        override fun run() {
            try {
                list.forEach { timer ->
                    if (timer.isActive) timer.time++
                }
            } finally {
                timers.postValue(list)
                handler.postDelayed(this, 1000L)
            }
        }
    }

    fun initializeTimers() {
        if (timers.value?.isNullOrEmpty() == false) return
        for (i in 0 until timerCount) list.add(Timer())
        timers.postValue(list)
    }

    fun toggleTimer(position: Int) {
        list[position].isActive = !list[position].isActive
    }

    fun toggleRunnable(enable: Boolean) {
        if (!enable) handler.removeCallbacks(runnable) else runnable.run()
    }

    class Timer {
        var time = 0
        var isActive = true
    }
}