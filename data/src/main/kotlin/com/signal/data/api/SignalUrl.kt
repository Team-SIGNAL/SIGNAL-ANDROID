package com.signal.data.api

object SignalUrl {
    private const val users = "/users"
    private const val feed = "/feed"
    private const val attachment = "/attachment"
    private const val diary = "/diary"
    private const val recommend = "/recommend"

    object Users {
        const val SignIn = "$users/signin"
        const val SignUp = "$users/signup"
        const val Secession = "$users/secession"
        const val FetchUserInformation = "$users/info"
    }

    object Feed {
        const val CreatePost = "$feed/user"
        const val List = "$feed/user/list"
        const val Details = "$feed/user/{feed_id}"
        const val Comments = "$feed/comment/{feed_id}"
        const val FeedId = "$feed/{feed_id}"
    }

    object Attachment {
        const val Upload = attachment
    }

    object Diary {
        const val CreateDiary = diary
        const val FetchAllDiaries = "$diary/list"
        const val FetchMonthDiaries = "$diary/month"
        const val FetchDiaryDetail = "$diary/details/{diary_id}"
        const val DeleteDiary = "$diary/{diary_id}"
    }

    object Recommend{
        const val FetchRecommends = "$recommend/list"
    }
}
