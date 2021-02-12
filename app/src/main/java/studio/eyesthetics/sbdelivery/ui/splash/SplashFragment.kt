package studio.eyesthetics.sbdelivery.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.viewModels
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.viewmodels.SplashViewModel
import studio.eyesthetics.sbdelivery.viewmodels.SplashViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class SplashFragment : BaseFragment<SplashViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@SplashFragment)
    }

    @Inject
    internal lateinit var splashViewModelFactory: SplashViewModelFactory

    override val layout: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModels {
        SavedStateViewModelFactory(splashViewModelFactory, this)
    }

    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {
        this.visibility = false
    }

    override fun setupViews() {
/*        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.navigate(NavigationCommand.To(R.id.homeFragment))
        }, 10000)*/
    }
}