package pro.fateeva.mvpapp.data

import pro.fateeva.mvpapp.domain.entities.User

interface UserRepository {
    fun addUser(user: User)
    fun getUser(login: String) : User?
}