package com.signal.signal_android

import android.app.Application
import com.signal.data.api.ApiProvider
import com.signal.data.datasource.user.local.LocalUserDataSource
import com.signal.data.datasource.user.local.LocalUserDataSourceImpl
import com.signal.data.datasource.user.remote.RemoteUserDataSource
import com.signal.data.datasource.user.remote.RemoteUserDataSourceImpl
import com.signal.data.repository.UserRepositoryImpl
import com.signal.data.util.TokenInterceptor
import com.signal.domain.repository.UserRepository
import com.signal.domain.usecase.users.SecessionUseCase
import com.signal.domain.usecase.users.SignInUseCase
import com.signal.domain.usecase.users.SignOutUseCase
import com.signal.domain.usecase.users.SignUpUseCase
import com.signal.domain.usecase.users.FetchUserInformationUseCase
import com.signal.signal_android.feature.mypage.MyPageViewModel
import com.signal.signal_android.feature.signup.SignUpViewModel
import com.signal.signal_android.feature.signin.SignInViewModel
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
        single { TokenInterceptor(get()) }
        single { ApiProvider.getUserApi(get()) }
    }

val dataSourceModule: Module
    get() = module {
        single<RemoteUserDataSource> { RemoteUserDataSourceImpl(get()) }
        single<LocalUserDataSource> { LocalUserDataSourceImpl(androidContext()) }
    }

val repositoryModule: Module
    get() = module {
        single<UserRepository> {
            UserRepositoryImpl(
                remoteUserDataSource = get(),
                localUserDataSource = get(),
            )
        }
    }

val useCaseModule: Module
    get() = module {
        single { SignInUseCase(get()) }
        single { SignUpUseCase(get()) }
        single { SignOutUseCase(get()) }
        single { SecessionUseCase(get()) }
        single { FetchUserInformationUseCase(get()) }
    }

val viewModelModule: Module
    get() = module {
        viewModel { SignInViewModel(get()) }
        viewModel { SignUpViewModel(get()) }
        viewModel {
            MyPageViewModel(
                signOutUseCase = get(),
                secessionUseCase = get(),
                fetchUserInformationUseCase = get(),
            )
        }
    }