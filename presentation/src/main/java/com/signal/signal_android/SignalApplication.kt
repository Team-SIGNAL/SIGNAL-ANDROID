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
import com.signal.domain.usecase.users.SignInUseCase
import com.signal.domain.usecase.users.SignUpUseCase
import com.signal.signal_android.feature.signup.SignUpViewModel
import com.signal.signal_android.viewmodel.SignInViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SignalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val apiModule = module {
            single { TokenInterceptor(get()) }
            single { ApiProvider.getUserApi(get()) }
        }

        val dataSourceModule = module {
            single<RemoteUserDataSource> { RemoteUserDataSourceImpl(get()) }
            single<LocalUserDataSource> { LocalUserDataSourceImpl(androidContext()) }
        }

        val repositoryModule = module {
            single<UserRepository> {
                UserRepositoryImpl(
                    remoteUserDataSource = get(),
                    localUserDataSource = get(),
                )
            }
        }

        val useCaseModule = module {
            single { SignInUseCase(get()) }
            single { SignUpUseCase(get()) }
        }

        val viewModelModule = module {
            viewModel { SignInViewModel(get()) }
            viewModel { SignUpViewModel(get()) }
        }

        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    apiModule,
                    dataSourceModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                ),
            )
        }
    }
}
