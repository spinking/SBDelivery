package studio.eyesthetics.sbdelivery.ui.auth

import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recovery_new_password.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.viewmodels.RecoveryNewPasswordViewModel
import studio.eyesthetics.sbdelivery.viewmodels.RecoveryNewPasswordViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class RecoveryNewPasswordFragment : BaseFragment<RecoveryNewPasswordViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@RecoveryNewPasswordFragment)
    }

    @Inject
    internal lateinit var recoverynewpasswordViewModelFactory: RecoveryNewPasswordViewModelFactory

    override val layout: Int = R.layout.fragment_recovery_new_password
    override val viewModel: RecoveryNewPasswordViewModel by viewModels {
        SavedStateViewModelFactory(recoverynewpasswordViewModelFactory, this)
    }

    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {}

    override fun setupViews() {
        main.toolbar.setNavigationOnClickListener {
            viewModel.navigate(NavigationCommand.PopUpToDestination(R.id.loginFragment))
        }

        btn_recovery_password.setOnClickListener {
            viewModel.recoveryPassword(et_password.text.toString(), ev_repeat_password.text.toString())
        }
    }
}