package studio.eyesthetics.sbdelivery.data.delegates

import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import studio.eyesthetics.sbdelivery.data.storage.Pref
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PrefDelegate<T>(
    private val defaultValue: T,
    private val preferences: SharedPreferences
) {
    private var storedValue: T? = null

    operator fun provideDelegate(
        thisRef: Pref,
        prop: KProperty<*>
    ): ReadWriteProperty<Pref, T> {
        val key = prop.name
        return object : ReadWriteProperty<Pref, T> {
            override fun getValue(thisRef: Pref, property: KProperty<*>): T {
                if (storedValue == null) {
                    @Suppress("UNCHECKED_CAST")
                    storedValue = when (defaultValue) {
                        is Int -> preferences.getInt(key, defaultValue as Int) as T
                        is Long -> preferences.getLong(key, defaultValue as Long) as T
                        is Float -> preferences.getFloat(key, defaultValue as Float) as T
                        is String -> preferences.getString(key, defaultValue as String) as T
                        is Boolean -> preferences.getBoolean(
                            key,
                            defaultValue as Boolean
                        ) as T
                        else -> error("This type can not be stored into Preferences")
                    }
                }
                return storedValue!!
            }

            override fun setValue(thisRef: Pref, property: KProperty<*>, value: T) {
                with(preferences.edit()) {
                    when (value) {
                        is String -> putString(key, value)
                        is Int -> putInt(key, value)
                        is Boolean -> putBoolean(key, value)
                        is Long -> putLong(key, value)
                        is Float -> putFloat(key, value)
                        else -> error("Only primitive types can be stored into Preferences")
                    }
                    apply()
                }
                storedValue = value
            }

        }
    }
}

class PrefObjDelegate<T>(
    private val adapter: JsonAdapter<T>
) {
    private var storedValue: T? = null

    operator fun provideDelegate(
        thisRef: Pref,
        prop: KProperty<*>
    ): ReadWriteProperty<Pref, T?> {
        val key = prop.name
        return object : ReadWriteProperty<Pref, T?> {
            override fun getValue(thisRef: Pref, property: KProperty<*>): T? {
                if (storedValue == null) {
                    storedValue = thisRef.mainPrefs.getString(key, null)?.let { adapter.fromJson(it) }
                }
                return storedValue
            }

            override fun setValue(thisRef: Pref, property: KProperty<*>, value: T?) {
                storedValue = value
                with(thisRef.mainPrefs.edit()) {
                    putString(key, value?.let { adapter.toJson(it) })
                    apply()
                }
            }
        }
    }
}