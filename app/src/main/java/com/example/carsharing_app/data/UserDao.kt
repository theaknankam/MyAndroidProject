package com.example.carsharing_app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ui_elemente.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users " +
            "WHERE username = :username\n" +
            "        AND password = :password\n" +
            "        LIMIT 1")
    suspend fun login(
        username: String,
        password: String
    ): User?

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int
}