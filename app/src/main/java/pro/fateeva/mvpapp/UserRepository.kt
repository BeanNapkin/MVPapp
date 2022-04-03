package pro.fateeva.mvpapp

class UserRepository {

    private val usersList = mutableListOf<User>()

    fun addUser(user: User) {
        usersList.add(user)
    }

    fun remindPassword(login: String): String? {
        return usersList.firstOrNull { user -> user.login == login }?.password
    }

    fun checkPassword(login: String, password: String): Response {
        val user = usersList.firstOrNull { user -> user.login == login }
            ?: return Response.UNREGISTERED

        return if (user?.password == password) {
            return Response.CORRECT_PASSWORD
        } else {
            return Response.UNCORRECT_PASSWORD
        }
    }

    fun checkDuplicationOfUsers(login: String): Boolean {
        return usersList.firstOrNull { user -> user.login == login } != null
    }

}