package com.alex_podolian.stackexchangetestapp.action.contract

import androidx.navigation.NavController

fun interface Action {
    suspend operator fun invoke(navController: NavController)
}