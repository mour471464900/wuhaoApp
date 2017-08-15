package com.anfeng.game

import com.google.gson.*
import java.lang.reflect.Type


object GsonHelper {

    val gson = create()

    fun create(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Boolean::class.java, BooleanAdapter())
                .registerTypeAdapter(Char::class.java, CharAdapter())
                .registerTypeAdapter(Short::class.java, ShortAdapter())
                .registerTypeAdapter(Int::class.java, IntAdapter())
                .registerTypeAdapter(Float::class.java, FloatAdapter())
                .registerTypeAdapter(Long::class.java, LongAdapter())
                .registerTypeAdapter(Double::class.java, DoubleAdapter())
                .registerTypeAdapter(String::class.java, StringAdapter())
//                .registerTypeHierarchyAdapter(List::class.java, ListAdapter())
                .create()
    }

    class BooleanAdapter : JsonDeserializer<Boolean> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Boolean {
            if (json!!.isJsonNull) {
                return false
            }
            return json.asBoolean
        }
    }

    class CharAdapter : JsonDeserializer<Char> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Char {
            if (json!!.isJsonNull) {
                return '0'
            }
            return json.asCharacter
        }
    }

    class ShortAdapter : JsonDeserializer<Short> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Short {
            if (json!!.isJsonNull) {
                return 0
            }
            return json.asShort
        }
    }

    class IntAdapter : JsonDeserializer<Int> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Int {
            if (json!!.isJsonNull) {
                return 0
            }
            return json.asInt
        }
    }

    class FloatAdapter : JsonDeserializer<Float> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Float {
            if (json!!.isJsonNull) {
                return 0f
            }
            return json.asFloat
        }
    }

    class LongAdapter : JsonDeserializer<Long> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Long {
            if (json!!.isJsonNull) {
                return 0L
            }
            return json.asLong
        }
    }

    class DoubleAdapter : JsonDeserializer<Double> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Double {
            if (json!!.isJsonNull) {
                return 0.0
            }
            return json.asDouble
        }
    }

    class StringAdapter : JsonDeserializer<String> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): String {
            if (json!!.isJsonNull) {
                return ""
            }
            return json.asString
        }
    }

    class ListAdapter : JsonDeserializer<List<Any>> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): List<Any> {
            if (json!!.isJsonNull || !json.isJsonArray) {
                return arrayListOf()
            }
            return create().fromJson(json, typeOfT)
        }
    }
}