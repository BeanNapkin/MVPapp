package pro.fateeva.mvpapp

import android.app.Application
import android.content.Context
import pro.fateeva.mvpapp.data.InMemoryUserRepository
import pro.fateeva.mvpapp.domain.LoginUsecase
import pro.fateeva.mvpapp.domain.LoginUsecaseImpl

class App : Application() {
    val loginUsecase: LoginUsecase by lazy {
        LoginUsecaseImpl(InMemoryUserRepository())
    }
}

val Context.app: App
    get() {
        return applicationContext as App
    }