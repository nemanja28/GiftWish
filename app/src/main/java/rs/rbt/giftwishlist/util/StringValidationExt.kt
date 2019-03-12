package rs.rbt.giftwishlist.util

import android.util.Patterns

/**
 * Created by Nemanja Stosic on 3/1/19.
 */


fun String.validateFullUrlAddress(): Boolean {
    return this.validateUrlAddress() && (this.startsWith(String.Http) || this.startsWith(String.Https))
}

fun String.validateUrlAddress(): Boolean {
    return Patterns.WEB_URL.matcher(this).matches()
}

val String.Companion.Http: String
    get() = "http://"

val String.Companion.Https: String
    get() = "https://"