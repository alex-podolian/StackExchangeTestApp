package com.alex_podolian.stackexchangetestapp.action

import androidx.navigation.NavController
import com.alex_podolian.stackexchangetestapp.action.contract.Action
import com.alex_podolian.stackexchangetestapp.data.model.User

class OpenUserDetailsScreen(var user: User) : Action {
    override suspend fun invoke(navController: NavController) {}
}