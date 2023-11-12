package com.signal.signal_android

import android.app.Application
import androidx.room.Room
import com.signal.data.api.ApiProvider
import com.signal.data.database.SignalDatabase
import com.signal.data.datasource.diagnosis.LocalDiagnosisDataSource
import com.signal.data.datasource.diagnosis.LocalDiagnosisDataSourceImpl
import com.signal.data.datasource.feed.FeedDataSource
import com.signal.data.datasource.feed.FeedDataSourceImpl
import com.signal.data.datasource.file.AttachmentDataSource
import com.signal.data.datasource.file.AttachmentDataSourceImpl
import com.signal.data.datasource.user.local.LocalUserDataSource
import com.signal.data.datasource.user.local.LocalUserDataSourceImpl
import com.signal.data.datasource.user.remote.RemoteUserDataSource
import com.signal.data.datasource.user.remote.RemoteUserDataSourceImpl
import com.signal.data.repository.AttachmentRepositoryImpl
import com.signal.data.repository.DiagnosisRepositoryImpl
import com.signal.data.repository.FeedRepositoryImpl
import com.signal.data.repository.UserRepositoryImpl
import com.signal.data.util.PreferenceManager
import com.signal.data.util.TokenInterceptor
import com.signal.domain.repository.AttachmentRepository
import com.signal.domain.repository.DiagnosisRepository
import com.signal.domain.repository.FeedRepository
import com.signal.domain.repository.UserRepository
import com.signal.domain.usecase.users.FetchUserInformationUseCase
import com.signal.domain.usecase.users.SecessionUseCase
import com.signal.domain.usecase.users.SignInUseCase
import com.signal.domain.usecase.users.SignOutUseCase
import com.signal.domain.usecase.users.SignUpUseCase
import com.signal.signal_android.feature.diagnosis.DiagnosisViewModel
import com.signal.signal_android.feature.file.AttachmentViewModel
import com.signal.signal_android.feature.main.feed.FeedViewModel
import com.signal.signal_android.feature.main.home.HomeViewModel
import com.signal.signal_android.feature.mypage.MyPageViewModel
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
            )
        }
        single {
            PreferenceManager(context = androidContext())
        }
    }

val dataSourceModule: Module
    get() = module {
        single<RemoteUserDataSource> { RemoteUserDataSourceImpl(userApi = get()) }
        single<LocalUserDataSource> { LocalUserDataSourceImpl(preferenceManager = get()) }
        single<FeedDataSource> { FeedDataSourceImpl(feedApi = get()) }
        single<AttachmentDataSource> { AttachmentDataSourceImpl(attachmentApi = get()) }
        single<LocalDiagnosisDataSource> {
            LocalDiagnosisDataSourceImpl(
                database = get(),
                preferenceManager = get(),
            )
        }
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
        single<DiagnosisRepository> {
            DiagnosisRepositoryImpl(localDiagnosisDataSource = get())
        }
    }

val useCaseModule: Module
    get() = module {
        single { SignInUseCase(userRepository = get()) }
        single { SignUpUseCase(userRepository = get()) }
        single { SignOutUseCase(userRepository = get()) }
        single { SecessionUseCase(userRepository = get()) }
        single { FetchUserInformationUseCase(userRepository = get()) }
    }

val viewModelModule: Module
    get() = module {
        viewModel { SignInViewModel(signInUseCase = get()) }
        viewModel { SignUpViewModel(signUpUseCase = get()) }
        viewModel {
            MyPageViewModel(
                signOutUseCase = get(),
                secessionUseCase = get(),
                fetchUserInformationUseCase = get(),
            )
        }
        viewModel { FeedViewModel(feedRepository = get()) }
        viewModel { AttachmentViewModel(attachmentRepository = get()) }
        viewModel { DiagnosisViewModel(diagnosisRepository = get()) }
        viewModel { HomeViewModel(diagnosisRepository = get()) }
    }
