package studio.eyesthetics.sbdelivery.ui.auth

import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recovery_password.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.viewmodels.RecoveryPasswordViewModel
import studio.eyesthetics.sbdelivery.viewmodels.RecoveryPasswordViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class RecoveryPasswordFragment : BaseFragment<RecoveryPasswordViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@RecoveryPasswordFragment)
    }

    @Inject
    internal lateinit var recoveryPasswordViewModelFactory: RecoveryPasswordViewModelFactory

    override val layout: Int = R.layout.fragment_recovery_password
    override val viewModel: RecoveryPasswordViewModel by viewModels {
        SavedStateViewModelFactory(recoveryPasswordViewModelFactory, this)
    }

    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {}

    override fun setupViews() {
        btn_enter.setOnClickListener {
            viewModel.recoverySendEmail(et_email.text.toString())
        }
    }
}