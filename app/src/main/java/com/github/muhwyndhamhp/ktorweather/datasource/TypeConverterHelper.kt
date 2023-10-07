package com.github.muhwyndhamhp.ktorweather.datasource

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.koin.core.component.KoinComponent


class TypeConverterHelper : KoinComponent {
    private val moshi = Moshi.Builder().build()
    private val listIntAdapter: JsonAdapter<List<Int?>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, Integer::class.java))
    private val listStringAdapter: JsonAdapter<List<String?>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, String::class.java))
    private val listFloatAdapter: JsonAdapter<List<Float?>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, Float::class.javaObjectType))
    @TypeConverter
    fun fromStringToListInt(value: String?): List<Int?>? {
        return value?.let {
            return listIntAdapter.fromJson(it)
        }
    }

    @TypeConverter
    fun fromListIntToString(list: List<Int?>?): String? {
        return listIntAdapter.toJson(list)
    }

    @TypeConverter
    fun fromStringToListString(value: String?): List<String?>? {
        return value?.let {
            return listStringAdapter.fromJson(it)
        }
    }

    @TypeConverter
    fun fromListStringToString(list: List<String?>?): String? {
        return listStringAdapter.toJson(list)
    }

    @TypeConverter
    fun fromStringToListFloat(value: String?): List<Float?>? {
        return value?.let {
            return listFloatAdapter.fromJson(it)
        }
    }

    @TypeConverter
    fun fromListFloatToString(list: List<Float?>?): String? {
        return listFloatAdapter.toJson(list)
    }
}