package com.step.examplechatgpt.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.step.examplechatgpt.model.Choice

@Dao
interface ChoiceDao {

    @Query("SELECT * FROM choice_table")
     fun getData(): LiveData<List<Choice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChannel(data: Choice)
}