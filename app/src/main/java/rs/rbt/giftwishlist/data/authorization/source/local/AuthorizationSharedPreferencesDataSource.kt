package rs.rbt.giftwishlist.data.authorization.source.local

import rs.rbt.giftwishlist.data.authorization.AuthorizationDataSource
import rs.rbt.giftwishlist.util.AppSharedPreferences

/**
 * Created by Nemanja Stosic on 3/8/19.
 */

object AuthorizationSharedPreferencesDataSource : AuthorizationDataSource {
    override fun register(username: String, password: String, email: String, onSuccess: () -> Unit, onFail: (() -> Unit)?) {
        //TODO: need to implement
    }

    override fun login(username: String, password: String, onSuccess: () -> Unit, onFail: (() -> Unit)?) {
        AppSharedPreferences.username = username
        AppSharedPreferences.password = password
        onSuccess()
    }

    override fun isLoggedIn(): Boolean {
        return AppSharedPreferences.username != null && AppSharedPreferences.password != null
    }

    override fun logout(onSuccess: () -> Unit, onFail: (() -> Unit)?) {
        AppSharedPreferences.username = null
        AppSharedPreferences.password = null
        onSuccess()
    }


}

