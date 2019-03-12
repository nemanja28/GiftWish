package rs.rbt.giftwishlist.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel;
import rs.rbt.giftwishlist.SingleLiveEvent
import rs.rbt.giftwishlist.data.authorization.AuthorizationRepository

class LoginViewModel(application: Application, private val authorizationRepository: AuthorizationRepository) : AndroidViewModel(application) {

    val onLoginSucceed =  SingleLiveEvent<Void>()
    val onLoginFailed =  SingleLiveEvent<String>()


    fun loginUser(username: String, password: String){
        authorizationRepository.login(username, password, onSuccess = {
            onLoginSucceed.call()
        }, onFail = {
            onLoginFailed.postValue("No reason")
        })
    }

}
