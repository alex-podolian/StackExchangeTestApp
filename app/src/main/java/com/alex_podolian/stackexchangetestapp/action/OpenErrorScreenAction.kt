package com.alex_podolian.stackexchangetestapp.action

import androidx.navigation.NavController
import com.alex_podolian.stackexchangetestapp.action.contract.Action

class OpenErrorScreenAction(val data: HashMap<*, *>) : Action {
    override suspend fun invoke(navController: NavController) {}
}