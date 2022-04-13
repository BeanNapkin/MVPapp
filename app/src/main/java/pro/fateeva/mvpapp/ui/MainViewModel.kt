package pro.fateeva.mvpapp.ui

import android.os.Looper
import java.lang.Thread.sleep
import android.os.Handler
import pro.fateeva.mvpapp.Publisher
import pro.fateeva.mvpapp.domain.Response
import pro.fateeva.mvpapp.domain.LoginUsecase

class MainViewModel(
    private val usecase: LoginUsecase
) {
    private val mainThreadHandler = Handler(Looper.getMainLooper())
    private var login: String = ""
    var response: Publisher<Response> = Publisher()
    var remindPasswordResponse: Publisher<String> = Publisher()
    val isShouldShowProgress: Publisher<Boolean> = Publisher()

    fun checkPassword(login: String, password: String): Response {
        return usecase.checkPassword(login, password)
    }

    fun onLogin(login: String, password: String) {
        isShouldShowProgress.post(true)
        Thread {
            sleep(3_000)
            mainThreadHandler.post {
                saveAndShowResponse(checkPassword(login, password))
            }
        }.start()
    }

    fun onForgetPassword(login: String) {
        isShouldShowProgress.post(true)
        this.login = login
        saveAndShowResponse(usecase.onForgetPassword(login))
    }

    fun onSignUp(login: String, password: String) {
        isShouldShowProgress.post(true)
        Thread {
            sleep(3_000)
            mainThreadHandler.post {
                saveAndShowResponse(usecase.onSignUp(login, password))
            }
        }.start()
    }

    private fun saveAndShowResponse(response: Response) {
        isShouldShowProgress.post(false)
        if (response == Response.YOUR_PASSWORD) {
            remindPasswordResponse.post(
                usecase.remindPassword(login) ?: error("No password for this $login")
            )
        } else {
            this.response.post(response)
        }
    }
}


