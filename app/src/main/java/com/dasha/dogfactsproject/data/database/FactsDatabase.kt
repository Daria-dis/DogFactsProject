package com.dasha.dogfactsproject.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dasha.dogfactsproject.data.model.Fact

@Database(entities = [Fact::class], version = 1)
abstract class FactsDatabase : RoomDatabase(){
    abstract fun factsDao(): FactsDao

    companion object {
        private var INSTANCE: FactsDatabase? = null

        fun getInstance(context : Context): FactsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FactsDatabase::class.java,
                    "facts"
                ).build()
                INSTANCE = instance
                instance
                }

            }
        }
    }