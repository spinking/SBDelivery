package studio.eyesthetics.sbdelivery.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.MainActivity
import studio.eyesthetics.sbdelivery.viewmodels.SplashViewModel

class SplashActivity : AppCompatActivity() {

    init {
        App.INSTANCE.appComponent.inject(this@SplashActivity)
    }

    val layout: Int = R.layout.activity_splash

    private val viewModel: SplashViewModel by viewModels()
    private var isNavigated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            if (isNavigated.not())
                navigateToMainActivity()
        }, 3000)

        viewModel.observeCurrentWork(this) {
            if (it[0].state.isFinished && isNavigated.not())
                navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        isNavigated = true
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }
}