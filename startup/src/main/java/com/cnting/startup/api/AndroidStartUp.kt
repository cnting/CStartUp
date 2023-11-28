package com.cnting.startup.api

import android.content.Context
import com.cnting.startup.dispatch.IDispatcher

/**
 * Created by cnting on 2023/11/27
 *
 */
interface AndroidStartUp<T>:IDispatcher {
    /**
     * 创建任务
     */
    fun createTask(context: Context): T

    /**
     * 依赖的任务
     */
    fun dependencies(): List<Class<out AndroidStartUp<*>>>

    /**
     * 入度数
     */
    fun getDependencyCount(): Int

}