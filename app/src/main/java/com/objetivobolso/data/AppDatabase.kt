package com.objetivobolso.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.objetivobolso.dao.GoalDao
import com.objetivobolso.entity.Goal

@Database(entities = [Goal::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun GoalDao():GoalDao

    companion object{

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{

            if(INSTANCE == null){

                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        name = "controle_cash"

                    ).build()
                }
                return  INSTANCE as AppDatabase

            }else{

                return INSTANCE as AppDatabase
            }

        }
    }
}