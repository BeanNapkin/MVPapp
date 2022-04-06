package pro.fateeva.mvpapp.ui

import android.os.Looper
import java.lang.Thread.sleep
import android.os.Handler
import pro.fateeva.mvpapp.domain.Response
import pro.fateeva.mvpapp.domain.LoginUsecase

class LoginPresenter(private val usecase: LoginUsecase) : LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private val mainThreadHandler = Handler(Looper.getMainLooper())
    private var response: Response? = null
    private var login: String = ""

    override fun onAttach(view: LoginContract.View) {
        this.view = view
        response?.let {
            saveAndShowResponse(it)
        }
    }

    override fun checkPassword(login: String, password: String): Response {
        return usecase.checkPassword(login, password)
    }

    override fun onLogin(login: String, password: String) {
        view?.showProgress()
        Thread {
            sleep(3_000)
            mainThreadHandler.post {
                view?.hideProgress()
                saveAndShowResponse(checkPassword(login, password))
            }
        }.start()
    }

    override fun onForgetPassword(login: String) {
        this.login = login
        saveAndShowResponse(usecase.onForgetPassword(login))
    }

    override fun onSignUp(login: String, password: String) {
        view?.showProgress()
        Thread {
            sleep(3_000)
            mainThreadHandler.post {
                view?.hideProgress()
                saveAndShowResponse(usecase.onSignUp(login, password))
            }
        }.start()
    }

    private fun saveAndShowResponse(response: Response) {
        if (response == Response.YOUR_PASSWORD) {
            view?.setResponse(
                response.response,
                usecase.remindPassword(login)
                    ?: error("Password for login $login not found")
            )
        } else {
            view?.setResponse(response.response)
        }
        this.response = response
    }
}

