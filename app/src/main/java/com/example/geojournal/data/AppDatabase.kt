package com.example.geojournal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.geojournal.model.JournalEntry

// This is the main database class.
// version = 1 because this is the first version of the database.
@Database(entities = [JournalEntry::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun journalDao(): JournalDao

    companion object {

        // we only want ONE database object in the whole app
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the database already exists, use it. If not, make it.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "geojournal_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}