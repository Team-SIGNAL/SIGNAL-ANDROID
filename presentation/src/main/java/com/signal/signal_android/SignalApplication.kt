package com.signal.signal_android

import android.app.Application
import com.signal.data.api.ApiProvider
import com.signal.data.datasource.feed.FeedDataSource
import com.signal.data.datasource.feed.FeedDataSourceImpl
import com.signal.data.datasource.file.FileDataSourceImpl
import com.signal.data.datasource.file.FileDatasource
import com.signal.data.datasource.user.local.LocalUserDataSource
import com.signal.data.datasource.user.local.LocalUserDataSourceImpl
import com.signal.data.datasource.user.remote.RemoteUserDataSource
import com.signal.data.datasource.user.remote.RemoteUserDataSourceImpl
import com.signal.data.repository.FeedRepositoryImpl
import com.signal.data.repository.FileRepositoryImpl
import com.signal.data.repository.UserRepositoryImpl
import com.signal.data.util.TokenInterceptor
import com.signal.domain.repository.FeedRepository
import com.signal.domain.repository.FileRepository
import com.signal.domain.repository.UserRepository
import com.signal.domain.usecase.users.FetchUserInformationUseCase
import com.signal.domain.usecase.users.SecessionUseCase
import com.signal.domain.usecase.users.SignInUseCase
import com.signal.domain.usecase.users.SignOutUseCase
import com.signal.domain.usecase.users.SignUpUseCase
import com.signal.signal_android.feature.file.FileViewModel
import com.signal.signal_android.feature.main.feed.FeedViewModel
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

val dataSourceModule: Module
    get() = module {
        single<RemoteUserDataSource> { RemoteUserDataSourceImpl(userApi = get()) }
        single<LocalUserDataSource> { LocalUserDataSourceImpl(context = androidContext()) }
        single<FeedDataSource> { FeedDataSourceImpl(feedApi = get()) }
        single<FileDatasource> { FileDataSourceImpl(fileApi = get()) }
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
        single<FileRepository> {
            FileRepositoryImpl(fileDatasource = get())
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
        viewModel { FileViewModel(fileRepository = get()) }
    }
