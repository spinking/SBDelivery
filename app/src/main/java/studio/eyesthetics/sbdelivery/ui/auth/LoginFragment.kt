package studio.eyesthetics.sbdelivery.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.viewModels
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
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class LoginFragment : BaseComposeFragment<LoginViewModel>() {

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
    override fun SetupLayout() = LoginLayout()

    override fun setupViews() {}
}

@Composable
fun LoginLayout() {
    val context = LocalContext.current
    Image(painter = painterResource(id = R.drawable.ic_background), contentDescription = "", modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Crop)
    ConstraintLayout(modifier = Modifier.padding(bottom = 36.dp)) {
        val (content, btnRestorePassword) = createRefs()
        Column(modifier = Modifier.constrainAs(content) {
            top.linkTo(parent.top)
            bottom.linkTo(btnRestorePassword.top)
        }) {
            CustomEditText(
                title = "Имя",
                errorMessage = "ошибка",
                hint = "Введите имя",
                modifier = Modifier
                    .padding(top = 38.dp),
                handler = { }
            )
            CustomEditText(
                title = "Имя",
                errorMessage = "ошибка",
                hint = "Введите имя",
                modifier = Modifier.padding(top = 16.dp),
                handler = { }
            )
            ActionButton(
                title = "Войти",
                Modifier.padding(top = 53.dp),
                onClick = {  }
            )
            ActionStrokeButton(
                title = "Регистрация",
                Modifier.padding(top = 20.dp),
                onClick = {  }
            )
        }
        TextButton(
            modifier = Modifier.constrainAs(btnRestorePassword) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onClick = {  }
        ) {
            Text(text = "Забыли пароль?", style = MaterialTheme.typography.h4, color = grey200)
        }
    }
}
