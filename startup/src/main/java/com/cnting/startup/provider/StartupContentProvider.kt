package com.cnting.startup.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.cnting.startup.StartupManager

/**
 * Created by cnting on 2023/11/27
 *
 */
class StartupContentProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        context?.let {
            val list = ProviderInitialize.initialMetaData(it)
            list.forEach {
                Log.d("===>", it.toString())
            }
            StartupManager.Builder().setAllTask(list).build().start(it).await()
        }
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }
}