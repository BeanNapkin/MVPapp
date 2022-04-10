package pro.fateeva.mvpapp.ui

import androidx.annotation.MainThread
import pro.fateeva.mvpapp.domain.Response

class LoginContract {

    interface View {
        @MainThread
        fun setResponse(response: Int)
        @MainThread
        fun showProgress()
        @MainThread
        fun hideProgress()
    }
}