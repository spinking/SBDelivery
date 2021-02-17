package studio.eyesthetics.sbdelivery.ui.auth

import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_registration.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.models.auth.RegistrationRequest
import studio.eyesthetics.sbdelivery.extensions.*
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.Binding
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.ui.delegates.RenderProp
import studio.eyesthetics.sbdelivery.viewmodels.RegistrationState
import studio.eyesthetics.sbdelivery.viewmodels.RegistrationViewModel
import studio.eyesthetics.sbdelivery.viewmodels.RegistrationViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class RegistrationFragment : BaseFragment<RegistrationViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@RegistrationFragment)
    }

    @Inject
    internal lateinit var registrationViewModelFactory: RegistrationViewModelFactory

    override val layout: Int = R.layout.fragment_registration
    override val viewModel: RegistrationViewModel by viewModels {
        SavedStateViewModelFactory(registrationViewModelFactory, this)
    }
    override val binding: RegistrationBinding by lazy { RegistrationBinding() }
    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {
        this.setTitle(getString(R.string.registration_label))
            .setBackButtonVisible(true)
    }

    override fun setupViews() {

        et_name.doOnTextChanged { text, start, before, count ->
            if (text.toString().isValidName() && text.toString().isNotEmpty()) {
                    tv_name_label.setErrorColor(false)
                    viewModel.handleValidName(true)
            } else {
                tv_name_label.setErrorColor(true)
                viewModel.handleValidName(false)
            }
        }

        et_surname.doOnTextChanged { text, start, before, count ->
            if (text.toString().isValidName() && text.toString().isNotEmpty()) {
                tv_name_label.setErrorColor(false)
                viewModel.handleValidSurname(true)
            } else {
                tv_name_label.setErrorColor(true)
                viewModel.handleValidSurname(false)
            }
        }

        et_email.doOnTextChanged { text, start, before, count ->
            if (text.toString().isValidEmail()) {
                tv_email_label.setErrorColor(false)
                tv_email_error.visible(false)
                viewModel.handleValidEmail(true)
            } else {
                tv_email_label.setErrorColor(true)
                tv_email_error.apply {
                    visible(true)
                    //text = getString(R.string.err)
                }
                viewModel.handleValidEmail(false)
            }
        }

        et_password.doOnTextChanged { text, start, before, count ->
            if (text.toString().isValidPassword()) {
                tv_password_label.setErrorColor(false)
                viewModel.handleValidPassword(true)
            } else {
                tv_password_label.setErrorColor(true)
                viewModel.handleValidPassword(false)
            }
        }

        btn_registration.setOnClickListener {
            viewModel.postRegistration(RegistrationRequest(
                et_name.text.toString(),
                et_surname.text.toString(),
                et_email.text.toString(),
                et_password.text.toString()
            ))
        }
    }

    inner class RegistrationBinding : Binding() {

        var buttonIsActivated: Boolean by RenderProp(false) {
            btn_registration.isActivated = it
        }

        override fun bind(data: IViewModelState) {
            data as RegistrationState
            buttonIsActivated= data.buttonIsActivated
        }
    }
}