package com.anfeng.game

import com.anfeng.wuhao.anfengkuaikan.base.MainApplication
import com.anfeng.wuhao.anfengkuaikan.utils.ACache
import com.google.gson.Gson
import java.lang.reflect.Type
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class ACacheProperty<T>(private val key: String, private val type: Type, private val valueChange: (oldValue: T?, newValue: T?) -> Unit = fun(_: T?, _: T?) {}) : ReadWriteProperty<Any?, T?> {
    private val cache by lazy {
        ACache.get(MainApplication.getContext())
    }
    private val gson by lazy {
        GsonHelper.gson
    }

    private var firstLoad = false
    private var value: T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        if (firstLoad) return value
        val json = cache.getAsString(key)
        var result: T? = null
        if (json != null) {
            try {
                result = gson.fromJson(json, type)
            } catch(e: Exception) {
            }
        }
        value = result
        firstLoad = true
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        try {
            valueChange(this.value, value)
            this.value = value
            val json = gson.toJson(value)
            cache.put(key, json)
        } catch(e: Exception) {
        }
    }

}