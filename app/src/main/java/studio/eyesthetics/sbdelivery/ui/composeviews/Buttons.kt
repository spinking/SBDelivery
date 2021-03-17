package studio.eyesthetics.sbdelivery.ui.composeviews

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import studio.eyesthetics.sbdelivery.ui.theme.orange900
import studio.eyesthetics.sbdelivery.ui.theme.white50Opacity20

@Composable
fun ActionButton(
    title: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(modifier = modifier) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 20.dp, end = 20.dp),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = orange900
            ),
            elevation = null
        ) {
            Text(text = title, style = MaterialTheme.typography.h4)
        }
    }
}

@Composable
fun ActionStrokeButton(
    title: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(modifier = modifier) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 20.dp, end = 20.dp),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            border = BorderStroke(1.dp, white50Opacity20),
            elevation = null
        ) {
            Text(text = title, style = MaterialTheme.typography.h4, color = MaterialTheme.colors.onPrimary)
        }
    }
}