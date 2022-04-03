package pro.fateeva.mvpapp

enum class Response(val response: String) {
    UNREGISTERED("Вы не зарегистированы"),
    USERS_DUPLICATION("Пользователь с таким именем уже существует"),
    EMPTY_CELLS("Заполните все поля"),
    REGISTER_SUCCESS("Вы успешно зарегистрированы"),
    CORRECT_PASSWORD("Верный пароль"),
    UNCORRECT_PASSWORD("Неверный пароль")
}