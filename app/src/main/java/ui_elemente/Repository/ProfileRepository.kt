package com.example.carsharing_app.data

import kotlinx.coroutines.flow.Flow
import com.example.carsharing_app.data.ProfileDao
import com.example.carsharing_app.data.ProfileEntity


class ProfileRepository(
    private val profileDao: ProfileDao
) {

    fun getProfile(): Flow<ProfileEntity?> {
        return profileDao.getProfile()
    }

    suspend fun saveProfile(profile: ProfileEntity) {
        profileDao.saveProfile(profile)
    }
}