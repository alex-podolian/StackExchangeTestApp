package com.alex_podolian.stackexchangetestapp.ui.composables

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alex_podolian.stackexchangetestapp.ui.theme.Teal100
import com.alex_podolian.stackexchangetestapp.ui.theme.Teal200

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    Box(
        modifier = modifier.padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        InfiniteProgress()
    }
}

@Composable
fun InfiniteProgress(modifier: Modifier = Modifier) {
    InfiniteProgressView(
        modifier = modifier,
        progressBackgroundColor = Teal100.copy(alpha = 0.2f),
        progressColor = Teal100,
        strokeWidth = 3.dp,
        strokeBackgroundWidth = 3.dp,
        roundedBorder = false,
        durationInMilliSecond = 700,
        radius = 20.dp
    )
}

@Composable
fun InfiniteProgressView(
    modifier: Modifier = Modifier,
    progressColor: Color = Teal100,
    progressBackgroundColor: Color = Teal200,
    strokeWidth: Dp = 10.dp,
    strokeBackgroundWidth: Dp = 10.dp,
    roundedBorder: Boolean = false,
    durationInMilliSecond: Int = 800,
    radius: Dp = 80.dp
) {
    val stroke = with(LocalDensity.current) {
        Stroke(
            width = strokeWidth.toPx(),
            cap = if (roundedBorder) StrokeCap.Round else StrokeCap.Square
        )
    }

    val strokeBackground = with(LocalDensity.current) {
        Stroke(width = strokeBackgroundWidth.toPx())
    }

    val transition = rememberInfiniteTransition()

    val animatedRestart by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(durationInMilliSecond, easing = LinearEasing))
    )

    Canvas(
        modifier
            .size(radius * 2)
    ) {
        val higherStrokeWidth =
            if (stroke.width > strokeBackground.width) stroke.width else strokeBackground.width
        val radius = (size.minDimension - higherStrokeWidth) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(
            halfSize.width - radius,
            halfSize.height - radius
        )
        val size = Size(radius * 2, radius * 2)

        drawArc(
            startAngle = 0f,
            sweepAngle = 360f,
            topLeft = topLeft,
            color = progressBackgroundColor,
            useCenter = false,
            size = size,
            style = strokeBackground
        )

        drawArc(
            color = progressColor,
            startAngle = animatedRestart,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = stroke,
        )
    }
}