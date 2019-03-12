package rs.rbt.giftwishlist.splash

import android.os.Bundle
import androidx.lifecycle.Observer
import org.jetbrains.anko.intentFor
import rs.rbt.giftwishlist.BaseAppCompatActivity
import rs.rbt.giftwishlist.R
import rs.rbt.giftwishlist.feed.FeedActivity
import rs.rbt.giftwishlist.login.LoginActivity
import rs.rbt.giftwishlist.util.extension.obtainViewModel

class SplashActivity : BaseAppCompatActivity(), SplashNavigator {

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, SplashFragment.newInstance()).commitNow()
        }
        splashViewModel = obtainSplashViewModel().apply {
            onLoginSuccessEvent.observe(this@SplashActivity, Observer {
                onLoginSucceed()
            })

            onLoginFailedEvent.observe(this@SplashActivity, Observer {
                onLoginFailed()
            })
        }
    }

    override fun onLoginSucceed() {
        startActivity(intentFor<FeedActivity>())
        finish()
    }

    override fun onLoginFailed() {
        startActivity(intentFor<LoginActivity>())
        finish()
    }


    fun obtainSplashViewModel(): SplashViewModel = obtainViewModel(SplashViewModel::class.java)
}
