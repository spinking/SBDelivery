package studio.eyesthetics.sbdelivery.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.base.BaseComposeFragment
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
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

    override fun setupViews() {}
    override fun setupLayout(): ComposeView = ComposeView(requireContext()).apply {
        val Colors = lightColors(
            primary = Color(0xFF24222C),
            secondary = Color(0xFFFFFFFF),
            background = Color(0xFF1A1A21),
            error = Color(0xFFEF3131)
        )
        setContent {
            MaterialTheme(
                colors = Colors
            ) {
                LoginLayout()
            }
        }
    }
}

@Composable
fun LoginLayout() {
    Image(painter = painterResource(id = R.drawable.ic_background), contentDescription = "", modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Crop)
    CustomEditText(title = "Имя", errorMessage = "ошибка", hint = "Введите имя", handler = { })
    CustomEditText(title = "Имя", errorMessage = "ошибка", hint = "Введите имя", handler = { })
}

@Composable
fun CustomEditText(
    title: String,
    errorMessage: String,
    hint: String,
    handler: (String) -> Unit
) {
    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colors
    var text by remember(calculation = { mutableStateOf("") })

    //TODO check name
    var isErrorVisible = false
    Column(
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(color = colors.secondary, text = title, style = typography.body2, modifier = Modifier.padding(start = 20.dp))
            if (isErrorVisible)
                Text(color = colors.error, text = errorMessage, style = typography.body2, modifier = Modifier.padding(end = 20.dp))
        }
        TextField(
            value = text,
            onValueChange = {
                text = it
                handler.invoke(it)
            },
            placeholder = {  Text(text = hint) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 6.dp, bottom = 0.dp),
            shape = RoundedCornerShape(6.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = colors.secondary
            ),
            singleLine = true
        )
    }
}
