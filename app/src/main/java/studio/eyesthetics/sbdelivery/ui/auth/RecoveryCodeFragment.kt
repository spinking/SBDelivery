package studio.eyesthetics.sbdelivery.ui.auth

import androidx.fragment.app.viewModels
import com.poovam.pinedittextfield.PinField
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recovery_code.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.viewmodels.RecoveryCodeViewModel
import studio.eyesthetics.sbdelivery.viewmodels.RecoveryCodeViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class RecoveryCodeFragment : BaseFragment<RecoveryCodeViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@RecoveryCodeFragment)
    }

    @Inject
    internal lateinit var recoverycodeViewModelFactory: RecoveryCodeViewModelFactory

    override val layout: Int = R.layout.fragment_recovery_code
    override val viewModel: RecoveryCodeViewModel by viewModels {
        SavedStateViewModelFactory(recoverycodeViewModelFactory, this)
    }

    override val prepareToolbar: (ToolbarBuilder.() -> Unit)? = {}

    override fun setupViews() {
        main.toolbar.setNavigationOnClickListener {
            viewModel.navigate(NavigationCommand.PopUpToDestination(R.id.loginFragment))
        }

        restore_code.onTextCompleteListener = object : PinField.OnTextCompleteListener {
            override fun onTextComplete(enteredText: String): Boolean {
                viewModel.sendRecoveryCode(enteredText)
                return false
            }
        }
    }
}