package rs.rbt.giftwishlist.util

import android.content.Context
import android.content.SharedPreferences
import rs.rbt.giftwishlist.WishListApplication

/**
 * Created by Nemanja Stosic on 3/6/19.
 */
object AppSharedPreferences {

    private const val DEFAULT_SHARED_PREFERENCES = "DEFAULT_SHARED_PREFERENCES"

    private const val PREF_USERNAME = "USERNAME"
    private const val PREF_PASSWORD = "PASSWORD"


    private fun getSharedPreferences(): SharedPreferences = WishListApplication.applicationContext().getSharedPreferences(DEFAULT_SHARED_PREFERENCES, Context.MODE_PRIVATE)


    private inline fun edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = getSharedPreferences().edit()
        operation(editor)
        editor.apply()
    }

    private fun setValue(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    private fun removeValue(key: String) {
        edit { it.remove(key) }
    }

    private inline fun <reified T : Any?> getValue(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> getSharedPreferences().getString(key, defaultValue as? String) as T?
            Int::class -> getSharedPreferences().getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getSharedPreferences().getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getSharedPreferences().getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getSharedPreferences().getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }


    var username: String? = null
        get() {
            if (field == null) {
                field = getValue(PREF_USERNAME)
            }
            return field
        }
        set(value) {
            if (value != null) {
                setValue(PREF_USERNAME, value)
            } else {
                removeValue(PREF_USERNAME)
            }
            field = value
        }

    var password: String? = null
        get() {
            if (field == null) {
                field = getValue(PREF_PASSWORD)
            }
            return field
        }
        set(value) {
            if (value != null) {
                setValue(PREF_PASSWORD, value)
            } else {
                removeValue(PREF_PASSWORD)
            }
            field = value
        }

}