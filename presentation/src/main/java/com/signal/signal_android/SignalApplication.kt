package com.signal.signal_android

import android.app.Application
import androidx.room.Room
import com.signal.data.api.ApiProvider
import com.signal.data.database.SignalDatabase
import com.signal.data.datasource.coin.CoinDataSource
import com.signal.data.datasource.coin.CoinDataSourceImpl
import com.signal.data.datasource.diagnosis.LocalDiagnosisDataSource
import com.signal.data.datasource.diagnosis.LocalDiagnosisDataSourceImpl
import com.signal.data.datasource.diary.DiaryDataSource
import com.signal.data.datasource.diary.DiaryDataSourceImpl
import com.signal.data.datasource.feed.FeedDataSource
import com.signal.data.datasource.feed.FeedDataSourceImpl
import com.signal.data.datasource.file.AttachmentDataSource
import com.signal.data.datasource.file.AttachmentDataSourceImpl
import com.signal.data.datasource.recommend.RecommendDataSource
import com.signal.data.datasource.recommend.RecommendDataSourceImpl
import com.signal.data.datasource.report.ReportDataSource
import com.signal.data.datasource.report.ReportDataSourceImpl
import com.signal.data.datasource.reservation.ReservationDataSource
import com.signal.data.datasource.reservation.ReservationDataSourceImpl
import com.signal.data.datasource.user.local.LocalUserDataSource
import com.signal.data.datasource.user.local.LocalUserDataSourceImpl
import com.signal.data.datasource.user.remote.RemoteUserDataSource
import com.signal.data.datasource.user.remote.RemoteUserDataSourceImpl
import com.signal.data.repository.AttachmentRepositoryImpl
import com.signal.data.repository.CoinRepositoryImpl
import com.signal.data.repository.DiagnosisRepositoryImpl
import com.signal.data.repository.DiaryRepositoryImpl
import com.signal.data.repository.FeedRepositoryImpl
import com.signal.data.repository.RecommendRepositoryImpl
import com.signal.data.repository.ReportRepositoryImpl
import com.signal.data.repository.ReservationRepositoryImpl
import com.signal.data.repository.UserRepositoryImpl
import com.signal.data.util.PreferenceManager
import com.signal.data.util.TokenInterceptor
import com.signal.domain.repository.AttachmentRepository
import com.signal.domain.repository.CoinRepository
import com.signal.domain.repository.DiagnosisRepository
import com.signal.domain.repository.DiaryRepository
import com.signal.domain.repository.FeedRepository
import com.signal.domain.repository.RecommendRepository
import com.signal.domain.repository.ReportRepository
import com.signal.domain.repository.ReservationRepository
import com.signal.domain.repository.UserRepository
import com.signal.domain.usecase.users.AddFamousSayingUseCase
import com.signal.domain.usecase.users.FetchUserInformationUseCase
import com.signal.domain.usecase.users.GetAccountIdUseCase
import com.signal.domain.usecase.users.GetDiagnosisHistoriesUseCase
import com.signal.domain.usecase.users.GetFamousSayingUseCase
import com.signal.domain.usecase.users.GetHistoryCountUseCase
import com.signal.domain.usecase.users.GetUserInformationUseCase
import com.signal.domain.usecase.users.SaveAccountIdUseCase
import com.signal.domain.usecase.users.SecessionUseCase
import com.signal.domain.usecase.users.SetUserInformationUseCase
import com.signal.domain.usecase.users.SignInUseCase
import com.signal.domain.usecase.users.SignOutUseCase
import com.signal.domain.usecase.users.SignUpUseCase
import com.signal.domain.usecase.users.UpdateUserInformationUseCase
import com.signal.signal_android.feature.bug.BugViewModel
import com.signal.signal_android.feature.coin.CoinViewModel
import com.signal.signal_android.feature.diagnosis.DiagnosisViewModel
import com.signal.signal_android.feature.file.AttachmentViewModel
import com.signal.signal_android.feature.main.diary.DiaryViewModel
import com.signal.signal_android.feature.main.feed.FeedViewModel
import com.signal.signal_android.feature.main.home.HomeViewModel
import com.signal.signal_android.feature.main.mypage.MyPageViewModel
import com.signal.signal_android.feature.main.recommend.RecommendViewModel
import com.signal.signal_android.feature.reservation.ReservationViewModel
import com.signal.signal_android.feature.signin.SignInViewModel
import com.signal.signal_android.feature.signup.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class SignalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SignalApplication)
            modules(signalModule)
        }
    }
}

val signalModule: Module
    get() = module {
        includes(
            apiModule,
            daoModule,
            dataSourceModule,
            repositoryModule,
            useCaseModule,
            viewModelModule,
        )
    }

val apiModule: Module
    get() = module {
        single { TokenInterceptor(localUserDataSource = get()) }
        single { ApiProvider.getUserApi(tokenInterceptor = get()) }
        single { ApiProvider.getFeedApi(tokenInterceptor = get()) }
        single { ApiProvider.getFileApi(tokenInterceptor = get()) }
        single { ApiProvider.getDiaryApi(tokenInterceptor = get()) }
        single { ApiProvider.getRecommendApi(tokenInterceptor = get()) }
        single { ApiProvider.getReservationApi(tokenInterceptor = get()) }
        single { ApiProvider.getCoinApi(tokenInterceptor = get()) }
        single { ApiProvider.getReportApi(tokenInterceptor = get()) }
    }

val daoModule: Module
    get() = module {
        single {
            Room.databaseBuilder(
                context = androidContext(),
                klass = SignalDatabase::class.java,
                name = "Signal-Database",
            ).build()
        }
        single {
            DBInitializer(
                context = androidContext(),
                database = get(),
                addFamousSayingUseCase = get(),
                getFamousSayingUseCase = get(),
            )
        }
        single {
            PreferenceManager(context = androidContext())
        }
    }

val dataSourceModule: Module
    get() = module {
        single<RemoteUserDataSource> { RemoteUserDataSourceImpl(userApi = get()) }
        single<LocalUserDataSource> {
            LocalUserDataSourceImpl(
                preferenceManager = get(),
                database = get(),
            )
        }
        single<FeedDataSource> { FeedDataSourceImpl(feedApi = get()) }
        single<AttachmentDataSource> { AttachmentDataSourceImpl(attachmentApi = get()) }
        single<LocalDiagnosisDataSource> {
            LocalDiagnosisDataSourceImpl(
                database = get(),
                preferenceManager = get(),
            )
        }
        single<DiaryDataSource> { DiaryDataSourceImpl(diaryApi = get()) }
        single<RecommendDataSource> { RecommendDataSourceImpl(recommendApi = get()) }
        single<ReservationDataSource> { ReservationDataSourceImpl(reservationApi = get()) }
        single<CoinDataSource> { CoinDataSourceImpl(coinApi = get()) }
        single<ReportDataSource> { ReportDataSourceImpl(reportApi = get()) }
    }

val repositoryModule: Module
    get() = module {
        single<UserRepository> {
            UserRepositoryImpl(
                remoteUserDataSource = get(),
                localUserDataSource = get(),
            )
        }
        single<FeedRepository> {
            FeedRepositoryImpl(feedDataSource = get())
        }
        single<AttachmentRepository> {
            AttachmentRepositoryImpl(attachmentDataSource = get())
        }
        single<DiagnosisRepository> {
            DiagnosisRepositoryImpl(localDiagnosisDataSource = get())
        }
        single<DiaryRepository> {
            DiaryRepositoryImpl(diaryDateSource = get())
        }
        single<RecommendRepository> {
            RecommendRepositoryImpl(recommendDataSource = get())
        }
        single<ReservationRepository> {
            ReservationRepositoryImpl(reservationDataSource = get())
        }
        single<CoinRepository> {
            CoinRepositoryImpl(coinDataSource = get())
        }
        single<ReportRepository> {
            ReportRepositoryImpl(reportDataSource = get())
        }
    }

val useCaseModule: Module
    get() = module {
        single { SignInUseCase(userRepository = get()) }
        single { SignUpUseCase(userRepository = get()) }
        single { SignOutUseCase(userRepository = get()) }
        single { SecessionUseCase(userRepository = get()) }
        single { FetchUserInformationUseCase(userRepository = get()) }
        single { SaveAccountIdUseCase(userRepository = get()) }
        single { GetAccountIdUseCase(userRepository = get()) }
        single { GetDiagnosisHistoriesUseCase(diagnosisRepository = get()) }
        single { GetFamousSayingUseCase(userRepository = get()) }
        single { AddFamousSayingUseCase(userRepository = get()) }
        single { GetUserInformationUseCase(userRepository = get()) }
        single { SetUserInformationUseCase(userRepository = get()) }
        single { UpdateUserInformationUseCase(userRepository = get()) }
        single { GetHistoryCountUseCase(diagnosisRepository = get()) }
    }

val viewModelModule: Module
    get() = module {
        viewModel {
            SignInViewModel(
                signInUseCase = get(),
                saveAccountIdUseCase = get(),
            )
        }
        viewModel { SignUpViewModel(signUpUseCase = get()) }
        viewModel {
            MyPageViewModel(
                signOutUseCase = get(),
                secessionUseCase = get(),
                fetchUserInformationUseCase = get(),
                getFamousSayingUseCase = get(),
                getUserInformationUseCase = get(),
                setUserInformationUseCase = get(),
                updateUserInformationUseCase = get(),
                userRepository = get(),
            )
        }
        viewModel { FeedViewModel(feedRepository = get()) }
        viewModel { AttachmentViewModel(attachmentRepository = get()) }
        viewModel {
            DiagnosisViewModel(
                diagnosisRepository = get(),
                getAccountIdUseCase = get(),
                getDiagnosisHistoriesUseCase = get(),
                getHistoryCountUseCase = get(),
            )
        }
        viewModel {
            HomeViewModel(
                getDiagnosisHistoriesUseCase = get(),
                getAccountIdUseCase = get(),
                getUserInformationUseCase = get(),
            )
        }
        viewModel { DiaryViewModel(diaryRepository = get()) }
        viewModel { RecommendViewModel(recommendRepository = get()) }
        viewModel { ReservationViewModel(reservationRepository = get()) }
        viewModel { CoinViewModel(coinRepository = get()) }
        viewModel { BugViewModel(reportRepository = get()) }
    }
