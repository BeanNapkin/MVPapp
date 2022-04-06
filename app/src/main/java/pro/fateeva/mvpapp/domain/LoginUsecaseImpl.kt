package pro.fateeva.mvpapp.domain

import pro.fateeva.mvpapp.data.UserRepository
import pro.fateeva.mvpapp.domain.entities.User

class LoginUsecaseImpl(private val repository: UserRepository) : LoginUsecase {

    override fun addUser(login: String, password: String) {
        repository.addUser(User(login, password))
    }

    override fun onForgetPassword(login: String): Response {
        if (login.isBlank()) {
            return Response.EMPTY_LOGIN
        } else {
            val password = remindPassword(login)
            if (password.isNullOrBlank()) {
                return Response.UNREGISTERED
            } else {
                return Response.YOUR_PASSWORD
            }
        }
    }

    override fun onSignUp(login: String, password: String): Response {
        if (login.isBlank() || password.isBlank()) {
            return Response.EMPTY_CELLS
        }
        if (isUserAlreadyRegistered(login)) {
            return Response.USERS_DUPLICATION
        }
        repository.addUser(User(login, password))
        return Response.REGISTER_SUCCESS
    }

    override fun remindPassword(login: String): String? {
        return repository.getUser(login)?.password
    }

    override fun checkPassword(login: String, password: String): Response {
        val user = repository.getUser(login)
            ?: return Response.UNREGISTERED
        return if (user.password == password) {
            Response.CORRECT_PASSWORD
        } else {
            Response.INCORRECT_PASSWORD
        }
    }

    override fun isUserAlreadyRegistered(login: String): Boolean {
        return repository.getUser(login) != null
    }
}