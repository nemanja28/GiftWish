package rs.rbt.giftwishlist.login

import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.jetbrains.anko.intentFor
import rs.rbt.giftwishlist.BaseAppCompatActivity
import rs.rbt.giftwishlist.R
import rs.rbt.giftwishlist.feed.FeedActivity
import rs.rbt.giftwishlist.util.extension.obtainViewModel
import kotlin.coroutines.CoroutineContext

class LoginActivity : BaseAppCompatActivity(), CoroutineScope, LoginNavigator {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, LoginFragment.newInstance()).commit()
        viewModel = obtainViewModel().apply {
            onLoginSucceed.observe(this@LoginActivity, Observer {
                onLoginSucceed()
            })
            onLoginFailed.observe(this@LoginActivity, Observer {
                onLoginFailed()
            })
        }
    }

    override fun onLoginSucceed() {
        startActivity(intentFor<FeedActivity>())
        finish()
    }

    override fun onLoginFailed() {
        //TODO: need to implement
    }


    fun obtainViewModel(): LoginViewModel = obtainViewModel(LoginViewModel::class.java)

}
