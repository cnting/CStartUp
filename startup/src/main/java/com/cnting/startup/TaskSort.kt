package com.cnting.startup

import android.util.Log
import com.cnting.startup.api.AndroidStartUp
import com.cnting.startup.api.SortStore

/**
 * Created by cnting on 2023/11/27
 *
 */
class TaskSort {
    private val inDegree: MutableMap<Class<out AndroidStartUp<*>>, Int> = mutableMapOf()

    private val nodeDependency: MutableMap<Class<out AndroidStartUp<*>>, MutableList<Class<out AndroidStartUp<*>>>> =
        mutableMapOf()
    private val taskStoreMap = mutableMapOf<Class<out AndroidStartUp<*>>, AndroidStartUp<*>>()

    private val queue: ArrayDeque<Class<out AndroidStartUp<*>>> = ArrayDeque()

    /**
     * 拓扑排序
     */
    fun sort(tasks: List<AndroidStartUp<*>>): SortStore {
        tasks.forEach { node ->
            taskStoreMap[node.javaClass] = node
            inDegree[node.javaClass] = node.getDependencyCount()
            if (node.getDependencyCount() == 0) {
                queue.addLast(node.javaClass)
            } else {
                node.dependencies().forEach { parent ->
                    var list = nodeDependency[parent]
                    if (list == null) {
                        list = mutableListOf()
                        nodeDependency[parent] = list
                    }
                    list.add(node.javaClass)
                }
            }
        }

        val result = mutableListOf<Class<out AndroidStartUp<*>>>()
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            result.add(node)
            nodeDependency[node]?.forEach { n ->
                inDegree[n] = inDegree[n]!! - 1
                if (inDegree[n]!! == 0) {
                    queue.add(n)
                }
            }
        }

        return SortStore(result, taskStoreMap, nodeDependency)
    }


}