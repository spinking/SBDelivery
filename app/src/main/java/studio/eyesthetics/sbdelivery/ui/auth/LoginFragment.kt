package studio.eyesthetics.sbdelivery.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.base.BaseComposeFragment
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.ui.composeviews.ActionButton
import studio.eyesthetics.sbdelivery.ui.composeviews.CustomEditText
import studio.eyesthetics.sbdelivery.ui.composeviews.ActionStrokeButton
import studio.eyesthetics.sbdelivery.ui.theme.grey200
import studio.eyesthetics.sbdelivery.viewmodels.LoginViewModel
import studio.eyesthetics.sbdelivery.viewmodels.LoginViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class LoginFragment : BaseComposeFragment<LoginViewModel>() {
    private val args: LoginFragmentArgs by navArgs()

    init {
        App.INSTANCE.appComponent.inject(this@LoginFragment)
    }

    @Inject
    internal lateinit var loginViewModelFactory: LoginViewModelFactory

    override val viewModel: LoginViewModel by viewModels {
        SavedStateViewModelFactory(loginViewModelFactory, this)
    }

    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {}

    @Composable
    override fun SetupLayout() = LoginLayout(viewModel, args.privateDestination)

    override fun setupViews() {}
}

@Composable
fun LoginLayout(loginViewModel: LoginViewModel, privateDestination: Int) {

    val nameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    //val context = LocalContext.current
    Image(painter = painterResource(id = R.drawable.ic_background), contentDescription = "", modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Crop)
    ConstraintLayout(modifier = Modifier.padding(bottom = 36.dp)) {
        val (content, btnRestorePassword) = createRefs()
        Column(modifier = Modifier.constrainAs(content) {
            top.linkTo(parent.top)
            bottom.linkTo(btnRestorePassword.top)
        }) {
            CustomEditText(
                title = stringResource(R.string.login_name_label),
                hint = stringResource(R.string.login_name_hint),
                modifier = Modifier
                    .padding(top = 38.dp),
                handler = {
                    nameState.value = it
                }
            )
            CustomEditText(
                title = stringResource(R.string.login_password_label),
                hint = stringResource(R.string.login_password_hint),
                modifier = Modifier.padding(top = 16.dp),
                handler = {
                    passwordState.value = it
                }
            )
            ActionButton(
                title = stringResource(R.string.login_enter_button),
                Modifier.padding(top = 53.dp),
                onClick = { loginViewModel.login(nameState.value, passwordState.value, if (privateDestination == -1) null else privateDestination) }
            )
            ActionStrokeButton(
                title = stringResource(R.string.login_registration_button),
                Modifier.padding(top = 20.dp),
                onClick = {
                    //TODO add last destination
                    loginViewModel.navigate(NavigationCommand.To(R.id.registrationFragment))
                }
            )
        }
        TextButton(
            modifier = Modifier.constrainAs(btnRestorePassword) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onClick = {
                //TODO add last destination
                loginViewModel.navigate(NavigationCommand.To(R.id.recoveryNewPasswordFragment))
            }
        ) {
            Text(text = stringResource(R.string.login_restore_password_button), style = MaterialTheme.typography.h4, color = grey200)
        }
    }
}
