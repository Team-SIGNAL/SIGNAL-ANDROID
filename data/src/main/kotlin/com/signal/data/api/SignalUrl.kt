package com.signal.data.api

object SignalUrl {
    private const val users = "/users"
    private const val feed = "/feed"
    private const val attachment = "/attachment"
    private const val diary = "/diary"
    private const val recommend = "/recommend"
    private const val reservation = "/reservation"
    private const val admin = "/admin"
    private const val coin = "/coin"
    private const val report = "/report"

    object Users {
        const val SignIn = "$users/signin"
        const val SignUp = "$users/signup"
        const val Secession = "$users/secession"
        const val FetchUserInformation = "$users/info"
        const val ProfileEdit = users
    }

    object Feed {
        const val CreatePost = "$feed/user"
        const val List = "$feed/user/list"
        const val Details = "$feed/user/{feed_id}"
        const val CreateComment = "/comment/user/{feed_id}"
        const val FetchComment = "/comment/{feed_id}"
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

    object Recommend {
        const val FetchRecommends = "$recommend/list"
        const val RecommendId = "$recommend/{recommend_id}"
    }

    object Reservation {
        const val FetchHospitals = "$admin/hospital/list"
        const val FetchDayReservation = "$reservation/user"
        const val CreateReservation = "$reservation/{hospital_id}"
        const val FetchReservationDetails = "$reservation/user/detail/{reservation_id}"
    }

    object Coin {
        const val CreateCoin = coin
        const val FetchCoins = "$coin/list"
    }

    object Report {
        const val ReportBug = report
    }
}
