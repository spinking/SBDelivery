package studio.eyesthetics.sbdelivery.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun DeliveryTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColors,
        typography = Typography.DeliveryTypography,
        shapes = DeliveryShapes,
        content = content
    )
}

private val LightColors = lightColors(
    primary = dark900,
    primaryVariant = dark900,
    onPrimary = white50,
    secondary = orange900,
    secondaryVariant = orange900Opacity60,
    onSecondary = white50,
    surface  = dark900,
    onSurface = grey200,
    background = black900,
    onBackground = black900,
    error = red600,
    onError = red600
)