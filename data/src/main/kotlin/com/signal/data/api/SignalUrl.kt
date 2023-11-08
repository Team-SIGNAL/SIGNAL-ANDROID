package com.signal.data.api

object SignalUrl {
    private const val users = "/users"
    private const val feed = "/feed"
    private const val attachment = "/attachment"

    object Users {
        const val SignIn = "$users/signin"
        const val SignUp = "$users/signup"
        const val Secession = "$users/secession"
        const val FetchUserInformation = "$users/info"
    }

    object Feed {
        const val CreatePost = "$feed/user"
        const val List = "$feed/list"
    }

    object Attachment {
        const val Upload = attachment
    }
}
