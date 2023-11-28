package com.cnting.startup.api

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import android.os.Process

/**
 * Created by cnting on 2023/11/27
 *
 */
abstract class AbsAndroidStartUp<T> : AndroidStartUp<T> {
    private val countDownLatch = CountDownLatch(getDependencyCount())

    override fun dependencies(): List<Class<out AndroidStartUp<*>>> {
        return emptyList()
    }

    override fun getDependencyCount(): Int {
        return dependencies().size
    }

    override fun executor(): Executor {
        return Executors.newFixedThreadPool(5)
    }

    override fun toWait() {
        countDownLatch.await()
    }

    override fun toCountDown() {
        countDownLatch.countDown()
    }

    override fun threadPriority(): Int {
        return Process.THREAD_PRIORITY_DEFAULT
    }

}