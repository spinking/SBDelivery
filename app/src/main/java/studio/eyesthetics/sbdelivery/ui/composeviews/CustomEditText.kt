package studio.eyesthetics.sbdelivery.ui.composeviews

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomEditText(
    title: String,
    errorMessage: String = "",
    isError: Boolean = false,
    hint: String,
    modifier: Modifier,
    handler: (String) -> Unit
) {
    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colors
    var text by remember(calculation = { mutableStateOf("") })

    Column(modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(color = colors.secondary, text = title, style = typography.body2, modifier = Modifier.padding(start = 20.dp))
            if (isError)
                Text(color = colors.error, text = errorMessage, style = typography.body2, modifier = Modifier.padding(end = 20.dp))
        }
        TextField(
            value = text,
            onValueChange = {
                text = it
                handler.invoke(it)
            },
            placeholder = {  Text(text = hint, color = colors.onSurface) },
            textStyle = typography.subtitle1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 6.dp, bottom = 0.dp),
            shape = RoundedCornerShape(6.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = colors.onPrimary,
                textColor = colors.surface,
            ),
            singleLine = true
        )
    }
}