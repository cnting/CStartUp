package com.cnting.startup

import com.cnting.startup.api.AndroidStartUp
import java.util.concurrent.ConcurrentHashMap
import com.cnting.startup.api.Result

/**
 * Created by cnting on 2023/11/27
 * 用于存储每个任务执行返回的结果
 */
class StartupResultManager {
    private val result: ConcurrentHashMap<Class<out AndroidStartUp<*>>, Result<*>> by lazy {
        ConcurrentHashMap()
    }

    fun saveResult(key: Class<out AndroidStartUp<*>>, value: Result<*>) {
        result[key] = value
    }

    fun getResult(key: Class<out AndroidStartUp<*>>): Result<*>? {
        return result[key]
    }

    companion object {
        val instance: StartupResultManager by lazy {
            StartupResultManager()
        }
    }
}