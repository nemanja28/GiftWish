package rs.rbt.giftwishlist.data.authorization

/**
 * Created by Nemanja Stosic on 3/7/19.
 */
class AuthorizationRepository(
        private val sharedPreferencesAuthorizationDataSource: AuthorizationDataSource,
        private val remoteAuthorizationDataSource: AuthorizationDataSource
) : AuthorizationDataSource {
    override fun register(username: String, password: String, email: String, onSuccess: () -> Unit, onFail: (() -> Unit)?) {
        remoteAuthorizationDataSource.register(username, password, email, onSuccess = {
            sharedPreferencesAuthorizationDataSource.register(username, password, email, onSuccess)
        }, onFail = onFail)
    }

    override fun login(username: String, password: String, onSuccess: () -> Unit, onFail: (() -> Unit)?) {
        if (isLoggedIn()) {
            onSuccess()
            return
        }

        remoteAuthorizationDataSource.login(username, password, onSuccess = {
            sharedPreferencesAuthorizationDataSource.login(username, password, onSuccess)
        }, onFail = onFail)

    }

    override fun isLoggedIn(): Boolean {
        return sharedPreferencesAuthorizationDataSource.isLoggedIn()
    }

    override fun logout(onSuccess: () -> Unit, onFail: (() -> Unit)?) {
        remoteAuthorizationDataSource.logout(onSuccess = {
            sharedPreferencesAuthorizationDataSource.logout(onSuccess)
        }, onFail = onFail)
    }


}