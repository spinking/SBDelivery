package studio.eyesthetics.sbdelivery.ui.auth

import android.widget.TextView
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

        et_name.doOnTextChanged { currentText, start, before, count ->
            if (currentText.toString().isNotEmpty()) {
                tv_name_label.setErrorColor(false)
                tv_name_error.visible(false)
                viewModel.handleEmptyName(true)
            } else {
                tv_name_label.setErrorColor(true)
                tv_name_error.apply {
                    text = getString(R.string.registration_empty_error)
                    visible(true)
                }
            }
        }

        et_surname.doOnTextChanged { currentText, start, before, count ->
            if (currentText.toString().isNotEmpty()) {
                tv_surname_label.setErrorColor(false)
                tv_surname_error.visible(false)
                viewModel.handleEmptySurname(true)
            } else {
                tv_surname_label.setErrorColor(true)
                tv_surname_error.apply {
                    text = getString(R.string.registration_empty_error)
                    visible(true)
                }
            }
        }

        et_email.doOnTextChanged { currentText, start, before, count ->
            if (currentText.toString().isNotEmpty()) {
                tv_email_label.setErrorColor(false)
                tv_email_error.visible(false)
                viewModel.handleEmptyEmail(true)
            } else {
                tv_email_label.setErrorColor(true)
                tv_email_error.apply {
                    visible(true)
                    text = getString(R.string.registration_empty_error)
                }
            }
        }

        et_password.doOnTextChanged { currentText, start, before, count ->
            if (currentText.toString().isNotEmpty()) {
                tv_password_label.setErrorColor(false)
                tv_password_error.visible(false)
                viewModel.handleEmptyPassword(true)
            } else {
                tv_password_label.setErrorColor(true)
                tv_password_error.apply {
                    text = getString(R.string.registration_empty_error)
                    visible(true)
                }
            }
        }

        btn_registration.setOnClickListener {
            viewModel.handleValidData(RegistrationRequest(
                et_name.text.toString(),
                et_surname.text.toString(),
                et_email.text.toString(),
                et_password.text.toString()
            ))
        }
    }

    private fun updateErrorViews(label: TextView, error: TextView, isNotEmpty: Boolean) {
        if (isNotEmpty) {
            label.setErrorColor(false)
            error.visible(false)
        } else {
            label.setErrorColor(true)
            error.apply {
                visible(true)
                text = getString(R.string.registration_empty_error)
            }
        }
    }

    private fun updateValidError(label: TextView, error: TextView, isValid: Boolean, errorMessageId: Int) {
        if (isValid) {
            label.setErrorColor(false)
            error.visible(false)
        } else {
            label.setErrorColor(true)
            error.apply {
                visible(true)
                text = getString(errorMessageId)
            }
        }
    }

    inner class RegistrationBinding : Binding() {

        var buttonIsActivated: Boolean by RenderProp(true) {
            btn_registration.isActivated = it
        }

        var isNotEmptyName: Boolean by RenderProp(true, false) {
            updateErrorViews(tv_name_label, tv_name_error, it)
        }
        var isNotEmptySurname: Boolean by RenderProp(true, false) {
            updateErrorViews(tv_surname_label, tv_surname_error, it)
        }
        var isNotEmptyEmail: Boolean by RenderProp(true, false) {
            updateErrorViews(tv_email_label, tv_email_error, it)
        }
        var isNotEmptyPassword: Boolean by RenderProp(true, false) {
            updateErrorViews(tv_password_label, tv_password_error, it)
        }

        var isValidName: Boolean by RenderProp(true, false) {
            updateValidError(tv_name_label, tv_name_error, it, R.string.registration_name_error)
        }
        var isValidSurname: Boolean by RenderProp(true, false) {
            updateValidError(tv_surname_label, tv_surname_error, it, R.string.registration_name_error)
        }
        var isValidEmail: Boolean by RenderProp(true, false) {
            updateValidError(tv_email_label, tv_email_error, it, R.string.registration_email_error )
        }

        override fun bind(data: IViewModelState) {
            data as RegistrationState
            isNotEmptyName = data.isNotEmptyName
            isNotEmptySurname = data.isNotEmptySurname
            isNotEmptyEmail = data.isNotEmptyEmail
            isNotEmptyPassword = data.isNotEmptyPassword
            isValidEmail = data.isValidEmail
            isValidName = data.isValidName
            isValidSurname = data.isValidSurname
            buttonIsActivated= data.buttonIsActivated
        }
    }
}