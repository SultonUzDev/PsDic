package com.uzdev.psdic.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uzdev.psdic.domain.model.Word

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao


    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getDatabaseInstance(context: Context): WordDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "word.db"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()


                INSTANCE = instance
                return instance
            }


        }


    }

}