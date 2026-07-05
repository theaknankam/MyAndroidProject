package ui_elemente.Repository

import com.example.carsharing_app.data.UserDao
import ui_elemente.model.User

class UserRepository(
    private val userDao : UserDao
) {
    suspend fun login(
        username: String,
        password: String): User? {

        return userDao.login(username, password)
    }
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUserCount(): Int {
        return userDao.getUserCount()
    }
}

