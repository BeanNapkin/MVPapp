package pro.fateeva.mvpapp

import android.os.Looper
import java.lang.Thread.sleep
import android.os.Handler

class LoginPresenter : LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private val mainThreadHandler = Handler(Looper.getMainLooper())
    private var responseText: String = ""

    private val userRepository: UserRepository = UserRepository()

    override fun onAttach(view: LoginContract.View) {
        this.view = view
        view.setResponse(responseText)
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
                responseText = response.response
            }
        }.start()
    }

    override fun onForgetPassword(login: String) {
        if (login == null){
            view?.setResponse(Response.EMPTY_LOGIN.response)
            responseText = Response.EMPTY_LOGIN.response
        } else {
            val password = userRepository.remindPassword(login)
            if (password == null){
                view?.setResponse(Response.UNREGISTERED.response)
                responseText = Response.UNREGISTERED.response
            } else {
                view?.setResponse(password)
                responseText = password
            }
        }
    }

    override fun onSignin(login: String, password: String) {
        view?.showProgress()
        Thread {
            sleep(3_000)
            mainThreadHandler.post {
                view?.hideProgress()
                if (login == "" || password == "") {
                    view?.setResponse(Response.EMPTY_CELLS.response)
                    responseText = Response.EMPTY_CELLS.response
                } else if (userRepository.checkDuplicationOfUsers(login)) {
                    view?.setResponse(Response.USERS_DUPLICATION.response)
                    responseText = Response.USERS_DUPLICATION.response
                } else {
                    userRepository.addUser(User(login, password))
                    view?.setResponse(Response.REGISTER_SUCCESS.response)
                    responseText = Response.REGISTER_SUCCESS.response
                }
            }
        }.start()
    }
}
