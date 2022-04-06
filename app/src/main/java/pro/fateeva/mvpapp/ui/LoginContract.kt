package pro.fateeva.mvpapp.ui

import androidx.annotation.MainThread
import pro.fateeva.mvpapp.domain.Response

class LoginContract {

    interface View {
        @MainThread
        fun setResponse(response: Int)
        @MainThread
        fun setResponse(response: Int, arg: String)
        @MainThread
        fun showProgress()
        @MainThread
        fun hideProgress()
    }

    interface Presenter {
        fun onAttach(view: View)
        fun checkPassword(login: String, password: String) : Response
        fun onLogin(login: String, password: String)
        fun onForgetPassword(password: String)
        fun onSignUp(login: String, password: String)
    }
}