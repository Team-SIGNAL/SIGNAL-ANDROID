package com.signal.data.api

object SignalUrl {
    private const val users = "/users"
    private const val feed = "/feed"

    object Users {
        const val SignIn = "$users/signin"
        const val SignUp = "$users/signup"
        const val Secession = "$users/secession"
        const val FetchUserInformation = "$users/info"
    }

    object Feed {
        const val Post = feed
        const val List = "$feed/list"
    }
}
