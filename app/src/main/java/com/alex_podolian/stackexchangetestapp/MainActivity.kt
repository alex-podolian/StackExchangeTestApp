package com.alex_podolian.stackexchangetestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alex_podolian.stackexchangetestapp.action.contract.ActionExecutor
import com.alex_podolian.stackexchangetestapp.ui.composables.TopBar
import com.alex_podolian.stackexchangetestapp.ui.composables.search.SearchScreen
import com.alex_podolian.stackexchangetestapp.ui.composables.user.UserDetailsScreen
import com.alex_podolian.stackexchangetestapp.ui.theme.Blue700
import com.alex_podolian.stackexchangetestapp.ui.theme.StackExchangeTestAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StackExchangeTestAppTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                val actionExecutor: ActionExecutor = { action ->
                    when (action) {
                        //TODO: implement action handling
                    }
                }

                Scaffold(
                    scaffoldState = scaffoldState,
                    backgroundColor = Blue700,
                    topBar = {
                        TopBar(
                            navController = navController,
                            title = resources.getString(R.string.app_name)
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
                            startDestination = "SearchScreen"
                        ) {
                            composable("SearchScreen") {
                                SearchScreen()
                            }
                            composable("UserDetailsScreen") {
                                UserDetailsScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}