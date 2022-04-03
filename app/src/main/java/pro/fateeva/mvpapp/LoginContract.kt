package pro.fateeva.mvpapp

import androidx.annotation.MainThread

class LoginContract {

    interface View {
        @MainThread
        fun setResponse(response: String)
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
        fun onSignin(login: String, password: String)
    }
}