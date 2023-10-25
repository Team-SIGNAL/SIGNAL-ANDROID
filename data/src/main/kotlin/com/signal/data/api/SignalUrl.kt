package com.signal.data.api

object SignalUrl {
    private const val users = "/users"

    object Users {
        const val SignIn = "$users/signin"
        const val SignUp = "$users/signup"
        const val Secession = "$users/secession"
    }
}
