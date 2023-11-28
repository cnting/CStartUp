package com.cnting.startup

import android.content.Context
import android.os.Looper
import com.cnting.startup.api.AndroidStartUp
import com.cnting.startup.dispatch.StartupRunnable
import java.util.concurrent.CountDownLatch

/**
 * Created by cnting on 2023/11/27
 *
 */
class StartupManager(private val list: List<AndroidStartUp<*>>) {
    private val mainCountDownLatch = CountDownLatch(list.size)

    fun start(context: Context): StartupManager {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw IllegalArgumentException("请在主线程中使用该框架")
        }
        val sortStore = TaskSort().sort(list)
        val finishTaskCallback = { task: AndroidStartUp<*> ->
            sortStore.notify(task)
            mainCountDownLatch.countDown()
        }
        sortStore.getResult().forEach { task ->
            val runnable = StartupRunnable(context, task, finishTaskCallback)

            if (task.callOnMainThread()) {
                runnable.run()
            } else {
                task.executor().execute(runnable)
            }
        }
        return this
    }

    /**
     * 等所有task执行完
     */
    fun await() {
        mainCountDownLatch.await()
    }

    class Builder {

        private val list: MutableList<AndroidStartUp<*>> by lazy {
            mutableListOf()
        }

        fun setTask(task: AndroidStartUp<*>): Builder {
            list.add(task)
            return this
        }

        fun setAllTask(tasks: List<AndroidStartUp<*>>): Builder {
            list.addAll(tasks)
            return this
        }

        fun build(): StartupManager {
            return StartupManager(list)
        }
    }
}