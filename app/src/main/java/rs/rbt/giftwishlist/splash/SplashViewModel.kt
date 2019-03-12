package rs.rbt.giftwishlist.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*
import rs.rbt.giftwishlist.SingleLiveEvent
import rs.rbt.giftwishlist.data.authorization.AuthorizationRepository
import kotlin.coroutines.CoroutineContext

class SplashViewModel(application: Application, private val authorizationRepository: AuthorizationRepository) : AndroidViewModel(application), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val onLoginSuccessEvent = SingleLiveEvent<Void>()
    val onLoginFailedEvent = SingleLiveEvent<String>()

    fun viewDidLoad() {
        launch {
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
