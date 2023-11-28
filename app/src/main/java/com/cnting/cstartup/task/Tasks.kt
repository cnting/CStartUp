package com.cnting.cstartup.task

import android.content.Context
import android.util.Log
import com.cnting.startup.StartupResultManager
import com.cnting.startup.api.AbsAndroidStartUp
import com.cnting.startup.api.AndroidStartUp
import java.util.concurrent.Callable
import java.util.concurrent.Executors

/**
 * Created by cnting on 2023/11/27
 *
 */
class Task1 : AbsAndroidStartUp<String>() {
    override fun createTask(context: Context): String {
        Log.e("TAG", "createTask Task1")
        return "Task1执行的结果"
    }

    override fun callOnMainThread(): Boolean {
        return false
    }

    override fun waitOnMainThread(): Boolean {
        return false
    }

}

class Task2 : AbsAndroidStartUp<String>() {
    override fun createTask(context: Context): String {
        val executor = Executors.newSingleThreadExecutor()
        val future = executor.submit(MyCallable())
        return future.get()
    }

    class MyCallable : Callable<String> {
        override fun call(): String {
            Thread.sleep(1500)
            return "任务2线程执行返回结果"
        }
    }

    override fun callOnMainThread(): Boolean {
        return false
    }

    override fun waitOnMainThread(): Boolean {
        return false
    }

    override fun dependencies(): List<Class<out AndroidStartUp<*>>> {
        return mutableListOf(Task1::class.java)
    }
}

class Task3 : AbsAndroidStartUp<String>() {
    override fun createTask(context: Context): String {
        Log.e("TAG", "createTask Task3")
        return ""
    }

    override fun dependencies(): List<Class<out AndroidStartUp<*>>> {
        return mutableListOf(Task1::class.java)
    }

    override fun callOnMainThread(): Boolean {
        return false
    }

    override fun waitOnMainThread(): Boolean {
        return false
    }
}

class Task4 : AbsAndroidStartUp<String>() {
    override fun createTask(context: Context): String {
        Log.e("TAG", "createTask Task4")
        val task2Result = StartupResultManager.instance.getResult(Task2::class.java)
        val data = task2Result?.data
        Log.e("TAG", "$data ---> 开始执行任务4")

        return ""
    }

    override fun dependencies(): List<Class<out AndroidStartUp<*>>> {
        return mutableListOf(Task2::class.java)
    }

    override fun callOnMainThread(): Boolean {
        return true
    }

    override fun waitOnMainThread(): Boolean {
        return false
    }
}

class Task5 : AbsAndroidStartUp<String>() {
    override fun createTask(context: Context): String {
        Log.e("TAG", "createTask Task5")
        return ""
    }

    override fun dependencies(): List<Class<out AndroidStartUp<*>>> {
        return mutableListOf(Task3::class.java, Task4::class.java)
    }

    override fun callOnMainThread(): Boolean {
        return false
    }

    override fun waitOnMainThread(): Boolean {
        return false
    }
}

