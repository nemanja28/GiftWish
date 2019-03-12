package rs.rbt.giftwishlist

import android.app.Application
import android.content.Context
import com.amitshekhar.DebugDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import kotlin.coroutines.CoroutineContext

/**
 * Created by Nemanja Stosic on 11/30/18.
 */
class WishListApplication: Application(), AnkoLogger, CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        warn { "DB debug Address = ${DebugDB.getAddressLog()}" }
        launch {

//            GiftLocalDataSource.getInstance()
        }
    }

    companion object {
        lateinit var instance: WishListApplication
            private set

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}