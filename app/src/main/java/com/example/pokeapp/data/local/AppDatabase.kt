package com.example.pokeapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 1. ADICIONADA ForumPostEntity::class E MUDADA A version = 2
@Database(entities = [UserEntity::class, GameAttemptEntity::class, ForumPostEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun gameAttemptDao(): GameAttemptDao
    abstract fun forumPostDao(): ForumPostDao // 2. ADICIONADO O NOVO DAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pokeapp_database"
                )
                    // 3. ADICIONADO: Permite que o Room destrua e recrie
                    // o banco de dados se a versão mudar. Evita crash.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}