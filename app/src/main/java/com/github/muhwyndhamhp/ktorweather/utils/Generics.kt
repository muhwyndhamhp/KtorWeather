package com.github.muhwyndhamhp.ktorweather.utils

class Generics {
    companion object {
        inline fun <reified T : Enum<T>, V> getEnumByValue(value: V, getValue: (T) -> V): T? {
            return enumValues<T>().firstOrNull { getValue(it) == value }
        }
    }
}