package com.alex_podolian.stackexchangetestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alex_podolian.stackexchangetestapp.action.OpenErrorScreenAction
import com.alex_podolian.stackexchangetestapp.action.OpenUserDetailsScreen
import com.alex_podolian.stackexchangetestapp.action.RetryAction
import com.alex_podolian.stackexchangetestapp.action.contract.ActionExecutor
import com.alex_podolian.stackexchangetestapp.data.model.User
import com.alex_podolian.stackexchangetestapp.ui.composables.ErrorScreen
import com.alex_podolian.stackexchangetestapp.ui.composables.TopBar
import com.alex_podolian.stackexchangetestapp.ui.composables.search.SearchScreen
import com.alex_podolian.stackexchangetestapp.ui.composables.user.UserDetailsScreen
import com.alex_podolian.stackexchangetestapp.ui.theme.Blue700
import com.alex_podolian.stackexchangetestapp.ui.theme.StackExchangeTestAppTheme
import java.io.Serializable

private const val NAV_ROUTE_USER_DETAILS_SCREEN = "UserDetailsScreen"
private const val NAV_ROUTE_SEARCH_SCREEN = "SearchScreen"
private const val NAV_ROUTE_ERROR_SCREEN = "ErrorScreen"

const val KEY_ERROR_TEXT = "errorText"
const val KEY_RETRY_ACTION = "retryAction"

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StackExchangeTestAppTheme {
                navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val topBarTitle = rememberSaveable { mutableStateOf(resources.getString(R.string.app_name)) }
                val showBackButton = rememberSaveable { mutableStateOf(false) }

                val actionExecutor: ActionExecutor = { action ->
                    when (action) {
                        is OpenUserDetailsScreen -> {
                            val bundle = Bundle()
                            bundle.putSerializable("user", action.user)
                            val resId = navController.findDestination(NAV_ROUTE_USER_DETAILS_SCREEN)?.id
                            resId?.let { navController.navigate(it, bundle) }
                        }
                        is OpenErrorScreenAction -> {
                            val bundle = Bundle()
                            bundle.putSerializable("data", action.data)
                            val resId = navController.findDestination(NAV_ROUTE_ERROR_SCREEN)?.id
                            resId?.let { navController.navigate(it, bundle) }
                        }
                    }
                }

                LaunchedEffect(navController) {
                    navController.currentBackStackEntryFlow.collect { backStackEntry ->
                        showBackButton.value = backStackEntry.destination.route.equals(NAV_ROUTE_USER_DETAILS_SCREEN)
                        topBarTitle.value =
                            when (backStackEntry.destination.route) {
                                NAV_ROUTE_USER_DETAILS_SCREEN -> resources.getString(R.string.user)
                                NAV_ROUTE_ERROR_SCREEN -> resources.getString(R.string.error)
                                else -> resources.getString(R.string.app_name)
                            }
                    }
                }

                Scaffold(
                    scaffoldState = scaffoldState,
                    backgroundColor = Blue700,
                    topBar = {
                        TopBar(
                            title = topBarTitle.value,
                            showBackButton = showBackButton.value,
                            navController = navController,
                        )
                    },
                    snackbarHost = {
                        //TODO: implement snackbar to inform user regarding occurred errors
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(
                            bottom = innerPadding.calculateBottomPadding(),
                            top = innerPadding.calculateTopPadding()
                        )
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = NAV_ROUTE_SEARCH_SCREEN
                        ) {
                            composable(NAV_ROUTE_SEARCH_SCREEN) {
                                SearchScreen(actionExecutor)
                            }
                            composable(NAV_ROUTE_USER_DETAILS_SCREEN) { backStackEntry ->
                                val user = backStackEntry.arguments?.getSerializable("user")
                                UserDetailsScreen(user as User, actionExecutor)
                            }
                            composable(NAV_ROUTE_ERROR_SCREEN) { backStackEntry ->
                                val data =
                                    backStackEntry.arguments?.getSerializable("data")
                                OpenErrorScreen(data)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun OpenErrorScreen(data: Serializable?) {
        val retryAction = (data as HashMap<*, *>)[KEY_RETRY_ACTION] as RetryAction
        val errorText = data[KEY_ERROR_TEXT] as String
        ErrorScreen(errorText) {
            navController.popBackStack()
            retryAction.invoke()
        }
    }
}