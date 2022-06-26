package com.alex_podolian.stackexchangetestapp.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.alex_podolian.stackexchangetestapp.R

@Composable
fun ImageWithDefaultPlaceholder(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    isRoundedCorners: Boolean = false,
    isRoundImage: Boolean = false,
    model: Any? = null,
    contentDescription: String? = null,
    placeholder: Painter? = painterResource(R.drawable.ic_placeholder),
    error: Painter? = null,
    fallback: Painter? = error,
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
) {
    val transformations = mutableListOf<Transformation>()
    if (isRoundedCorners) {
        transformations.add(RoundedCornersTransformation(radius = LocalDensity.current.run { 2.dp.toPx() }.toFloat()))
    }
    if (isRoundImage) {
        transformations.add(RoundedCornersTransformation(radius = LocalDensity.current.run { 30.dp.toPx() }.toFloat()))
    }
    AsyncImage(
        model = model ?: ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(500)
            .transformations(transformations)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        placeholder = placeholder,
        error = error,
        fallback = fallback,
        onLoading = onLoading,
        onSuccess = onSuccess,
        onError = onError,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality
    )
}