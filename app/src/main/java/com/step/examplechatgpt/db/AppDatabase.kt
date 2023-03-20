package com.step.examplechatgpt.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.step.examplechatgpt.dao.ChoiceDao
import com.step.examplechatgpt.model.Choice

@Database(entities = [Choice::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun choiceDao(): ChoiceDao
}