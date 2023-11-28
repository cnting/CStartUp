package com.cnting.startup.dispatch

import android.content.Context
import com.cnting.startup.StartupManager
import com.cnting.startup.api.AndroidStartUp
import android.os.Process
import com.cnting.startup.StartupResultManager
import com.cnting.startup.api.Result

/**
 * Created by cnting on 2023/11/27
 *
 */
class StartupRunnable(
    private val context: Context,
    private val task: AndroidStartUp<*>,
    private val notify: (AndroidStartUp<*>) -> Unit
) :
    Runnable {
    override fun run() {
        Process.setThreadPriority(task.threadPriority())
        //等依赖的task执行完
        task.toWait()
        val result = task.createTask(context)
        StartupResultManager.instance.saveResult(task.javaClass, Result(result))
        //执行自己task的countDown
        notify(task)
    }
}