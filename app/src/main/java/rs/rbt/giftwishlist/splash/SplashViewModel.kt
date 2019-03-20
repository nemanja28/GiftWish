package rs.rbt.giftwishlist.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import rs.rbt.giftwishlist.SingleLiveEvent
import rs.rbt.giftwishlist.data.authorization.AuthorizationRepository
import kotlin.coroutines.CoroutineContext

class SplashViewModel(application: Application, private val authorizationRepository: AuthorizationRepository) : AndroidViewModel(application) {

    val onLoginSuccessEvent = SingleLiveEvent<Void>()
    val onLoginFailedEvent = SingleLiveEvent<String>()

    fun viewDidLoad() {
        viewModelScope.launch {
            delay(3000)
            loginUserAutomaticallyIfPossible()
        }
    }

    private fun loginUserAutomaticallyIfPossible() {
        if (authorizationRepository.isLoggedIn()) {
            onLoginSuccessEvent.call()
        } else {
            onLoginFailedEvent.value = NO_REASON_TEXT
        }
    }


    companion object {
        const val NO_REASON_TEXT = ""
    }

}
