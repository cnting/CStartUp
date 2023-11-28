package com.cnting.startup.dispatch

import java.util.concurrent.Executor

/**
 * Created by cnting on 2023/11/27
 *
 */
interface IDispatcher {
    /**
     * 是否在主线程中执行
     */
    fun callOnMainThread(): Boolean

    /**
     * 是否需要等待该任务完成
     */
    fun waitOnMainThread(): Boolean

    /**
     * 等待父任务执行完成
     */
    fun toWait()

    /**
     * 父任务执行完成
     */
    fun toCountDown()
    fun executor(): Executor
    fun threadPriority(): Int

}