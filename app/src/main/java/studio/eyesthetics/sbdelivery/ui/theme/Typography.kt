package studio.eyesthetics.sbdelivery.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import studio.eyesthetics.sbdelivery.R

object Typography {

    private val Roboto = FontFamily(
        Font(R.font.font_roboto_medium, FontWeight.W500),
        Font(R.font.font_roboto_regular, FontWeight.W400),
        Font(R.font.font_roboto_light, FontWeight.W300)
    )

    val DeliveryTypography = Typography(
        h3 = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.W500,
            fontSize = 24.sp
            ),
        h4 = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp
        ),
        h5 = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp
        ),
        h6 = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp
        ),
        subtitle1 = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp
        ),
        subtitle2 = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.W500,
            fontSize = 12.sp
        ),
        body1 = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.W300,
            fontSize = 13.sp
        ),
        body2 = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.W300,
            fontSize = 12.sp
        ),
        button = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp
        ),
        caption = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.W400,
            fontSize = 12.sp
        )
    )
}