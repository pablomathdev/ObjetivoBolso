package com.objetivobolso.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.objetivobolso.entity.Goal


@Dao
interface GoalDao {

    @Insert
    fun insert(goal: Goal)

    @Query(value = "SELECT * FROM Goal")
    fun getAll():List<Goal>

    @Query(value = "SELECT * FROM Goal WHERE id =:goalId")
    fun getById(goalId:Int):Goal

    @Query(value = "DELETE FROM Goal WHERE id = :goalId")
    fun delete(goalId:Int)

    @Query(value = "UPDATE Goal SET current_value = :value WHERE id = :goalId")
    fun update(goalId: Int,value:Double)

    @Query(value = "UPDATE Goal SET goal_value = :value, goal_title = :title WHERE id = :goalId")
    fun updateTitleAndValue(goalId:Int,value: Double,title:String)






}