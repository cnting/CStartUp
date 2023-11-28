package com.cnting.startup.provider

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.cnting.startup.api.AndroidStartUp

/**
 * Created by cnting on 2023/11/27
 *
 */
object ProviderInitialize {
    private const val META_KEY = "app_startup"
    private const val CONTENT_PROVIDER_NAME = "com.cnting.startup.provider.StartupContentProvider"
    fun initialMetaData(context: Context): List<AndroidStartUp<*>> {
        val provider = ComponentName(context, CONTENT_PROVIDER_NAME)
        val providerInfo =
            context.packageManager.getProviderInfo(provider, PackageManager.GET_META_DATA)
        val nodeMap = mutableMapOf<Class<*>, AndroidStartUp<*>>()
        providerInfo.metaData.keySet().forEach {
            val dataKey = providerInfo.metaData.get(it)
            if (dataKey == META_KEY) {
                doInitializeTask(context, Class.forName(it), nodeMap)
            }
        }
        return nodeMap.values.toList()
    }

    /**
     * 传入最后一个task，倒着查所有依赖的task
     */
    private fun doInitializeTask(
        context: Context,
        clazz: Class<*>,
        nodeMap: MutableMap<Class<*>, AndroidStartUp<*>>
    ) {
        val starUp = clazz.newInstance() as AndroidStartUp<*>
        if (!nodeMap.containsKey(clazz)) {
            nodeMap[clazz] = starUp
        }
        //查找依赖项
        starUp.dependencies().forEach {
            doInitializeTask(context, it, nodeMap)
        }
    }
}