package pro.fateeva.mvpapp.domain

import pro.fateeva.mvpapp.R

enum class Response(val response: Int) {
    UNREGISTERED(R.string.you_are_not_registered),
    USERS_DUPLICATION(R.string.A_user_with_this_name_already_exists),
    EMPTY_CELLS(R.string.fill_in_all_the_fields),
    EMPTY_LOGIN(R.string.enter_your_login),
    REGISTER_SUCCESS(R.string.you_have_successfully_registered),
    CORRECT_PASSWORD(R.string.correct_password),
    INCORRECT_PASSWORD(R.string.incorrect_password),
    YOUR_PASSWORD(R.string.your_password_is)
}