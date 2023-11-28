package com.cnting.startup.api

/**
 * Created by cnting on 2023/11/27
 *
 */
class SortStore(
    private val result: MutableList<Class<out AndroidStartUp<*>>>,
    private val startupStore: MutableMap<Class<out AndroidStartUp<*>>, AndroidStartUp<*>>,
    private val dependency: MutableMap<Class<out AndroidStartUp<*>>, MutableList<Class<out AndroidStartUp<*>>>>
) {

    private val mainThreadTasks = mutableListOf<AndroidStartUp<*>>()
    private val ioThreadTasks = mutableListOf<AndroidStartUp<*>>()
    fun getResult(): List<AndroidStartUp<*>> {
        val list = mutableListOf<AndroidStartUp<*>>()
        result.forEach {
            if (startupStore[it]?.callOnMainThread() == true) {
                mainThreadTasks.add(startupStore[it]!!)
            } else {
                ioThreadTasks.add(startupStore[it]!!)
            }
        }

        list.addAll(ioThreadTasks)
        list.addAll(mainThreadTasks)
        return list
    }

    fun notify(task: AndroidStartUp<*>) {
        dependency[task.javaClass]?.forEach {
            startupStore[it]?.toCountDown()
        }
    }
}