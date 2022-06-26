package com.alex_podolian.stackexchangetestapp.ui.composables

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alex_podolian.stackexchangetestapp.R
import com.alex_podolian.stackexchangetestapp.ui.theme.Blue800
import com.alex_podolian.stackexchangetestapp.ui.theme.Gray100
import com.alex_podolian.stackexchangetestapp.ui.theme.Gray600
import com.alex_podolian.stackexchangetestapp.ui.theme.Teal100
import com.alex_podolian.stackexchangetestapp.utils.ConnectionState
import com.alex_podolian.stackexchangetestapp.utils.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ErrorScreen(errorText: String, reloadAction: () -> Unit) {
    val connection by LocalContext.current.connectivityState()
    val isConnected = connection === ConnectionState.Available
    val context = LocalContext.current as Activity
    val resources = LocalContext.current.resources
    val titleText = if (isConnected) errorText else resources.getString(R.string.noInternetConnection)
    val descriptionText =
        if (isConnected) resources.getString(R.string.tryLater) else resources.getString(R.string.checkInternet)
    val btnRetryText = resources.getString(R.string.retry)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(R.drawable.ic_no_connection),
                contentDescription = "Error occurred",
                contentScale = ContentScale.Fit,
            )
            Text(
                modifier = Modifier.padding(top = 24.dp, start = 36.dp, end = 36.dp),
                text = titleText,
                textAlign = TextAlign.Center,
                style = TextStyle(color = Color.White, fontSize = 14.sp)
            )
            Text(
                modifier = Modifier.padding(top = 14.dp, start = 36.dp, end = 36.dp),
                text = descriptionText,
                textAlign = TextAlign.Center,
                style = TextStyle(color = Gray600, fontSize = 12.sp)
            )
            if (isConnected) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, start = 36.dp, end = 36.dp)
                        .height(32.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Teal100),
                    shape = RoundedCornerShape(7F, 7F, 7F, 7F),
                    contentPadding = PaddingValues(bottom = 0.dp),
                    onClick = { if (isConnected) reloadAction() }
                ) {
                    Text(
                        text = btnRetryText,
                        style = TextStyle(
                            color = Blue800,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            } else {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, start = 36.dp, end = 36.dp)
                        .height(32.dp),
                    enabled = isConnected,
                    shape = RoundedCornerShape(7F, 7F, 7F, 7F),
                    border = BorderStroke(1.dp, Gray100),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
                    onClick = {},
                ) {
                    Text(
                        text = btnRetryText,
                        style = TextStyle(
                            color = Gray100,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
    }

    BackHandler(enabled = true) {
        context.finish()
    }
}