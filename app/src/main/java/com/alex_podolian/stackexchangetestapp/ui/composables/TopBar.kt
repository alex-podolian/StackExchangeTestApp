package com.alex_podolian.stackexchangetestapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alex_podolian.stackexchangetestapp.ui.theme.Blue600

@Composable
fun TopBar(
    title: String,
    showBackButton: Boolean = false,
    navController: NavController? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Blue600)
    ) {
        if (showBackButton) {
            IconButton(
                onClick = { navController?.popBackStack() },
                modifier = Modifier.layoutId("iconButton")
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Back button",
                    tint = MaterialTheme.colors.surface,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = title,
                color = MaterialTheme.colors.surface,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}