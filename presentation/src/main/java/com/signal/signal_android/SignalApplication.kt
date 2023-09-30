package com.signal.signal_android

import android.app.Application
import com.signal.data.api.ApiProvider
import com.signal.data.datasource.user.UserDataSource
import com.signal.data.datasource.user.UserDataSourceImpl
import com.signal.data.repository.UserRepositoryImpl
import com.signal.domain.repository.UserRepository
import com.signal.domain.usecase.SignInUseCase
import com.signal.signal_android.viewmodel.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SignalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val apiModule = module {
            single { ApiProvider.getUserApi() }
        }

        val dataSourceModule = module {
            single<UserDataSource> { UserDataSourceImpl(get()) }
        }

        val repositoryModule = module {
            single<UserRepository> { UserRepositoryImpl(get()) }
        }

        val useCaseModule = module {
            single { SignInUseCase(get()) }
        }

        val viewModelModule = module {
            viewModel { SignInViewModel(get()) }
        }

        startKoin {
            modules(
                listOf(
                    apiModule,
                    dataSourceModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                )
            )
        }
    }
}