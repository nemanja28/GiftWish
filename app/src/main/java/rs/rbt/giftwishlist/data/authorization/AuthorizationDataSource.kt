package rs.rbt.giftwishlist.data.authorization

/**
 * Created by Nemanja Stosic on 3/7/19.
 */
interface AuthorizationDataSource {

    fun register(username: String, password: String, email: String, onSuccess: () -> Unit, onFail: (() -> Unit)? = null)
    fun login(username: String, password: String, onSuccess: () -> Unit, onFail: (() -> Unit)? = null)
    fun isLoggedIn(): Boolean
    fun logout(onSuccess: () -> Unit, onFail: (() -> Unit)? = null)

}