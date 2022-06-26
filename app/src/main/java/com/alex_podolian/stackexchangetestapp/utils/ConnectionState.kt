package com.alex_podolian.stackexchangetestapp.utils

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}