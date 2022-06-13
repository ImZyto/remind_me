package com.example.remin.model.db

import android.content.Context
import androidx.room.*
import com.example.remin.model.converters.Converter
import com.example.remin.model.dao.TasksDao
import com.example.remin.model.dataclass.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tasksDao(): TasksDao

    companion object{
        @Volatile
        private var database: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = database
            if (tempInstance != null)
                return tempInstance
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                ).build()

                database = instance
                return instance
            }
        }
    }

}