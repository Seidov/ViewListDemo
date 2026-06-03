package com.sultanseidov.viewlistdemo2.data.local.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class RoomConverters {

    @TypeConverter
    fun fromIntList(value: List<Int>?): String? = value?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toIntList(value: String?): List<Int>? {
        if (value == null) return null
        return try {
            Json.decodeFromString<List<Int>>(value)
        } catch (e: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun fromLongList(value: List<Long>?): String? = value?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toLongList(value: String?): List<Long>? {
        if (value == null) return null
        return try {
            Json.decodeFromString<List<Long>>(value)
        } catch (e: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String? = value?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        if (value == null) return null
        return try {
            Json.decodeFromString<List<String>>(value)
        } catch (e: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun fromStringDoubleMap(value: Map<String, Double>?): String? = value?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toStringDoubleMap(value: String?): Map<String, Double>? {
        if (value == null) return null
        return try {
            Json.decodeFromString<Map<String, Double>>(value)
        } catch (e: Exception) {
            emptyMap()
        }
    }

    @TypeConverter
    fun fromIntDoubleMap(value: Map<Int, Double>?): String? = value?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toIntDoubleMap(value: String?): Map<Int, Double>? {
        if (value == null) return null
        return try {
            Json.decodeFromString<Map<Int, Double>>(value)
        } catch (e: Exception) {
            emptyMap()
        }
    }
}
