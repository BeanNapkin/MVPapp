package pro.fateeva.mvpapp.domain

interface LoginUsecase {
    fun addUser(login: String, password: String)
    fun onForgetPassword(login: String) : Response
    fun onSignUp(login: String, password: String): Response
    fun remindPassword(login: String) : String?
    fun checkPassword(login: String, password: String): Response
    fun isUserAlreadyRegistered(login: String): Boolean
}