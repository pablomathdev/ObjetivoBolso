package com.objetivobolso.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Goal(

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo("goal_title")
    val goalTitle: String,

    @ColumnInfo("goal_value")
    val goalValue: Double,

    @ColumnInfo("current_value")
    val currentValue: Double,

    @ColumnInfo("img_goal_id")
    val imgGoal: Int


)
