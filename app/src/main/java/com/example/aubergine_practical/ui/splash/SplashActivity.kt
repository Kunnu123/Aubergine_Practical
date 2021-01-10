package com.example.aubergine_practical.ui.splash

import androidx.appcompat.app.AppCompatActivity
import com.example.aubergine_practical.model.UserHolder
import com.example.aubergine_practical.ui.auth.login.LoginActivity
import com.example.aubergine_practical.ui.workout.WorkoutActivity
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

/**
 * splash screen
 */
class SplashActivity : AppCompatActivity() {
    private lateinit var mCoroutineScope: CoroutineScope
    private val mUserHolder: UserHolder? by inject()

    /**
     * Launch screen based on session exist or not?
     */
    override fun onResume() {
        super.onResume()
        mCoroutineScope = CoroutineScope(Dispatchers.Main)
        mCoroutineScope.launch {
            delay(TimeUnit.SECONDS.toMillis(2))
            mUserHolder?.let {
                if (it.mAccessToken.isNullOrBlank()) {
                    LoginActivity.start(this@SplashActivity)
                } else {
                    WorkoutActivity.start(this@SplashActivity)
                }
            } ?: let {
                LoginActivity.start(this@SplashActivity)
            }
            finish()
            overridePendingTransition(0, 0)
        }
    }

    override fun onStop() {
        super.onStop()
        mCoroutineScope.cancel()
    }
}