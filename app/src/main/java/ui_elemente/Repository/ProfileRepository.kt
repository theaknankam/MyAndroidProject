package com.example.carsharing_app.data

import kotlinx.coroutines.flow.Flow
import ui_elemente.model.ProfileEntity


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