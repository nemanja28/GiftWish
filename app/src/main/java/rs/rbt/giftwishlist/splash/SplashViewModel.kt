package rs.rbt.giftwishlist.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.broadcast
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import rs.rbt.giftwishlist.SingleLiveEvent
import rs.rbt.giftwishlist.data.authorization.AuthorizationRepository
import kotlin.coroutines.CoroutineContext

class SplashViewModel(application: Application, private val authorizationRepository: AuthorizationRepository) : AndroidViewModel(application), AnkoLogger {

    val onLoginSuccessEvent = SingleLiveEvent<Void>()
    val onLoginFailedEvent = SingleLiveEvent<String>()

    private var uiScope: CoroutineScope? = null

    fun viewDidLoad() {
        val viewModelJob = SupervisorJob()
        uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
        uiScope?.launch {
            delay(SIMULATED_LOADING_DELAY_IN_MS)
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

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        uiScope?.cancel()
    }

    companion object {
        const val SIMULATED_LOADING_DELAY_IN_MS = 3000L
        const val NO_REASON_TEXT = ""
    }
}
