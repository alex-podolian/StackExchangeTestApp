package com.alex_podolian.stackexchangetestapp.ui.composables.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alex_podolian.stackexchangetestapp.KEY_RETRY_ACTION
import com.alex_podolian.stackexchangetestapp.action.OpenErrorScreenAction
import com.alex_podolian.stackexchangetestapp.action.OpenUserDetailsScreen
import com.alex_podolian.stackexchangetestapp.action.RetryAction
import com.alex_podolian.stackexchangetestapp.action.contract.ActionExecutor
import com.alex_podolian.stackexchangetestapp.core.presentation.search.SearchEffect
import com.alex_podolian.stackexchangetestapp.core.presentation.search.SearchIntent
import com.alex_podolian.stackexchangetestapp.core.presentation.search.SearchState
import com.alex_podolian.stackexchangetestapp.data.model.User
import com.alex_podolian.stackexchangetestapp.ui.composables.LoadingIndicator
import com.alex_podolian.stackexchangetestapp.ui.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    executor: ActionExecutor? = null,
) {
    val viewModel = viewModel<SearchViewModel>()
    val state by viewModel.state.collectAsState(initial = SearchState())

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchEffect.NavigateToErrorScreen -> executor?.let {
                    val retryAction = object : RetryAction() {
                        override fun invoke() {
                            state.queryInput?.let { viewModel.dispatch(SearchIntent.SubmitQuery(it)) }
                        }
                    }
                    effect.data[KEY_RETRY_ACTION] = retryAction
                    it(OpenErrorScreenAction(effect.data))
                }
            }
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            SearchBar(
                modifier = Modifier
                    .weight(3f)
                    .height(36.dp)
                    .padding(end = 16.dp)
                    .background(color = Blue600, shape = RoundedCornerShape(2.dp)),
                placeholder = "Search",
                keyboardController = keyboardController,
                queryInput = state.queryInput,
                onQueryChange = { viewModel.dispatch(SearchIntent.ChangeQuery(it)) },
                onQuerySubmit = {
                    state.queryInput?.let { if (it.length >= 2) viewModel.dispatch(SearchIntent.SubmitQuery(it)) }
                }
            )
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    state.queryInput?.let { if (it.length >= 2) viewModel.dispatch(SearchIntent.SubmitQuery(it)) }
                    keyboardController?.hide()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Teal100),
                shape = RoundedCornerShape(7F, 7F, 7F, 7F),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "Search",
                    style = TextStyle(
                        color = Blue800,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
        if (state.isLoading) {
            LoadingIndicator()
            return@Column
        }
        val lazyListState = rememberLazyListState()
        state.users?.let { users ->
            if (users.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 4.dp),
                    state = lazyListState
                ) {
                    itemsIndexed(users) { _, item ->
                        UserListItem(item, executor)
                    }
                }
            }
        }
    }
}

@Composable
fun UserListItem(
    user: User,
    executor: ActionExecutor?
) {
    Box(
        modifier = Modifier
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable { executor?.let { it(OpenUserDetailsScreen(user)) } }
                .fillMaxWidth()
                .height(36.dp)
                .background(color = Blue370, shape = RoundedCornerShape(2.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = user.reputation.toString(),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = user.name,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    placeholder: String,
    keyboardController: SoftwareKeyboardController?,
    queryInput: String?,
    onQueryChange: (String) -> Unit,
    onQuerySubmit: () -> Unit
) {
    Box(
        modifier = modifier.border(1.dp, Color.DarkGray, RoundedCornerShape(2.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                this@Row.AnimatedVisibility(visible = queryInput.isNullOrBlank()) {
                    Text(
                        text = placeholder,
                        style = TextStyle(color = Gray370, fontSize = 14.sp)
                    )
                }
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = queryInput ?: "",
                    onValueChange = onQueryChange,
                    textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onQuerySubmit()
                            keyboardController?.hide()
                        }
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(Teal100)
                )
            }
        }
    }
}