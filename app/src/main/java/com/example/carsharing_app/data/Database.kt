package com.example.carsharing_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ui_elemente.model.ChatMessageEntity
import ui_elemente.model.ProfileEntity
import ui_elemente.model.User
import ui_elemente.model.enums.Trip

@Database(entities = [Trip::class, ProfileEntity::class, ChatMessageEntity::class, User::class], version = 7)
/*Room can only store primitive types (Strings, Integers, etc.).
If your entities contain complex objects (like a Date or a custom Enum),
you need a Converters class to tell Room how to convert those objects
into a format it can store and back again.*/
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tripDao(): TripDao
    abstract fun profileDao(): ProfileDao
    abstract fun chatDao(): ChatDao
    abstract fun userDao() : UserDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "carsharing_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
