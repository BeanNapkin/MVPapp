package pro.fateeva.mvpapp

import android.os.Looper
import java.lang.Thread.sleep
import android.os.Handler

class LoginPresenter : LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private val mainThreadHandler = Handler(Looper.getMainLooper())
    private var response: Response? = null
    private val userRepository: UserRepository = UserRepository()
    private var login: String = ""

    override fun onAttach(view: LoginContract.View) {
        this.view = view
        response?.let {
            saveAndShowResponse(it)
        }
    }

    override fun checkPassword(login: String, password: String): Response {
        return userRepository.checkPassword(login, password)
    }

    override fun onLogin(login: String, password: String) {
        view?.showProgress()
        Thread {
            sleep(3_000)
            mainThreadHandler.post {
                view?.hideProgress()
                val response = checkPassword(login, password)
                view?.setResponse(response.response)
                this.response = response
            }
        }.start()
    }

    override fun onForgetPassword(login: String) {
        this.login = login
        if (login.isBlank()) {
            saveAndShowResponse(Response.EMPTY_LOGIN)
        } else {
            val password = userRepository.remindPassword(login)
            if (password.isNullOrBlank()) {
                saveAndShowResponse(Response.UNREGISTERED)
            } else {
                saveAndShowResponse(Response.YOUR_PASSWORD)
            }
        }
    }

    override fun onSignin(login: String, password: String) {
        if (login.isBlank() || password.isBlank()) {
            view?.setResponse(Response.EMPTY_CELLS.response)
            response = Response.EMPTY_CELLS
        } else {
            view?.showProgress()
            Thread {
                sleep(3_000)
                mainThreadHandler.post {
                    view?.hideProgress()
                    if (userRepository.checkDuplicationOfUsers(login)) {
                        saveAndShowResponse(Response.USERS_DUPLICATION)
                    } else {
                        userRepository.addUser(User(login, password))
                        saveAndShowResponse(Response.REGISTER_SUCCESS)
                    }
                }
            }.start()
        }
    }

    private fun saveAndShowResponse(response: Response) {
        if (response == Response.YOUR_PASSWORD) {
            view?.setResponse(
                response.response,
                userRepository.remindPassword(login)
                    ?: error("Password for login $login not found")
            )
        } else {
            view?.setResponse(response.response)
        }
        this.response = response
    }
}
