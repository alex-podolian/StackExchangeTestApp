package com.alex_podolian.stackexchangetestapp.ui.composables.user

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alex_podolian.stackexchangetestapp.KEY_RETRY_ACTION
import com.alex_podolian.stackexchangetestapp.R
import com.alex_podolian.stackexchangetestapp.action.OpenErrorScreenAction
import com.alex_podolian.stackexchangetestapp.action.RetryAction
import com.alex_podolian.stackexchangetestapp.action.contract.ActionExecutor
import com.alex_podolian.stackexchangetestapp.core.presentation.userdetails.UserDetailsEffect
import com.alex_podolian.stackexchangetestapp.core.presentation.userdetails.UserDetailsIntent
import com.alex_podolian.stackexchangetestapp.core.presentation.userdetails.UserDetailsState
import com.alex_podolian.stackexchangetestapp.data.model.User
import com.alex_podolian.stackexchangetestapp.ui.composables.ImageWithDefaultPlaceholder
import com.alex_podolian.stackexchangetestapp.ui.composables.LoadingIndicator
import com.alex_podolian.stackexchangetestapp.utils.formatDate
import java.lang.String.format

@Composable
fun UserDetailsScreen(
    user: User,
    executor: ActionExecutor? = null,
) {
    val viewModel = viewModel<UserDetailsViewModel>(factory = UserDetailsViewModelFactory(user.id.toString()))
    val state by viewModel.state.collectAsState(initial = UserDetailsState())

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is UserDetailsEffect.NavigateToErrorScreen -> executor?.let {
                    val retryAction = object : RetryAction() {
                        override fun invoke() {
                            viewModel.dispatch(UserDetailsIntent.FetchTopTags(user.id.toString()))
                        }
                    }
                    effect.data[KEY_RETRY_ACTION] = retryAction
                    it(OpenErrorScreenAction(effect.data))
                }
            }
        }
    }

    if (state.isLoading) {
        LoadingIndicator()
        return
    }
    val resources = LocalContext.current.resources

    state.topTags?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ImageWithDefaultPlaceholder(
                modifier = Modifier
                    .size(200.dp),
                imageUrl = user.profileImageUrl,
                isRoundedCorners = true
            )
            TextItem(format(resources.getString(R.string.userName), user.name))
            TextItem(format(resources.getString(R.string.reputation), user.reputation.toString()))
            val topTags = state.topTags
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                TextItem(
                    if (topTags.isNullOrEmpty()) resources.getString(R.string.topTagsNA)
                    else resources.getString(R.string.topTags)
                )
                if (!topTags.isNullOrEmpty()) {
                    var tags = ""
                    topTags.forEachIndexed { index, topTag ->
                        val topTagText = if (topTags.size == index + 1) "#${topTag.tagName}" else "#${topTag.tagName}, "
                        tags = tags.plus(topTagText)
                    }
                    TextItem(tags)
                }
            }
            val badges = resources.getString(R.string.badges)
                .plus(format(resources.getString(R.string.gold), user.badges.gold.toString()))
                .plus(format(resources.getString(R.string.silver), user.badges.silver.toString()))
                .plus(format(resources.getString(R.string.bronze), user.badges.bronze.toString()))
            TextItem(badges)
            TextItem(
                format(
                    resources.getString(R.string.location),
                    user.location ?: resources.getString(R.string.na)
                )
            )
            TextItem(format(resources.getString(R.string.creationDate), user.creationDate.formatDate()))
        }
    }
}

@Composable
fun TextItem(text: String) {
    Text(
        text = text,
        style = TextStyle(
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Start
        ),
        maxLines = 5,
        overflow = TextOverflow.Ellipsis
    )
}