package rs.rbt.giftwishlist.data.authorization.source.remote

import rs.rbt.giftwishlist.Injection
import rs.rbt.giftwishlist.data.authorization.AuthorizationDataSource

/**
 * Created by Nemanja Stosic on 3/8/19.
 */
object AuthorizationRemoteDataSource : AuthorizationDataSource {
    override fun register(username: String, password: String, email: String, onSuccess: () -> Unit, onFail: (() -> Unit)?) {
        //TODO: need to implement
        onSuccess()
    }

    override fun login(username: String, password: String, onSuccess: () -> Unit, onFail: (() -> Unit)?) {
        val mockedCredentials = Injection.provideAuthCredentials()
        if (mockedCredentials.first == username && mockedCredentials.second == password) onSuccess() else onFail?.invoke()
    }

    override fun isLoggedIn(): Boolean {
        //TODO: need to implement
        return true
    }

    override fun logout(onSuccess: () -> Unit, onFail: (() -> Unit)?) {
        //TODO: need to implement
        onSuccess()
    }

}