package pro.fateeva.mvpapp.data

import pro.fateeva.mvpapp.domain.entities.User

class InMemoryUserRepository : UserRepository {

    private val usersList = mutableListOf<User>()

    override fun addUser(user: User) {
        usersList.add(user)
    }

    override fun getUser(login: String): User? {
        return usersList.firstOrNull { user -> user.login == login }
    }
}