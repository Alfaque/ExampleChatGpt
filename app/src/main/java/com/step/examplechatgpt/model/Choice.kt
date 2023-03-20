package com.step.examplechatgpt.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "choice_table")
data class Choice(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val finish_reason: String,
    val index: Int,
    val text: String,
    var sender: String,
    var dateTime: Long
)