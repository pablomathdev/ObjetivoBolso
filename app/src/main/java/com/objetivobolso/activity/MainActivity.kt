package com.objetivobolso.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.objetivobolso.App
import com.objetivobolso.R
import com.objetivobolso.activity.listener.OnClickListener
import com.objetivobolso.entity.Goal
import helpers.formatDoubleToBRL
import helpers.showHelpActivity
import java.text.NumberFormat
import java.util.Locale


class MainActivity : AppCompatActivity(), OnClickListener<Goal>{

    private lateinit var valueSavedTotalTextView:TextView

    private lateinit var goalRecyclerView : RecyclerView

    private lateinit var goals: List<Goal>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


         Thread{


             val app = application as App
             val dao = app.database.GoalDao()

             goals = dao.getAll()



             var count = 0.0
             for(goal in goals){

                 count += goal.currentValue
             }

             valueSavedTotalTextView = findViewById(R.id.value_saved_total)
             val valueSavedTotalFormatted = formatDoubleToBRL(count)
//             valueSavedTotalTextView.text = valueSavedTotalFormatted

             val sharedPreferences = getSharedPreferences("valueTotal",Context.MODE_PRIVATE)
             val sharedPreferencesEdit = sharedPreferences.edit()
             sharedPreferencesEdit.putString("valueTotal",valueSavedTotalFormatted )
             sharedPreferencesEdit.apply()


             runOnUiThread{

                 valueSavedTotalTextView.text = valueSavedTotalFormatted


                 goalRecyclerView = findViewById(R.id.goals_recycler_view)
                val containerGoalsTitleAndSort:ConstraintLayout = findViewById(R.id.container_goals_title_and_sort)
                 val containerInfo:LinearLayout = findViewById(R.id.container_info)
                 val noObjective:ConstraintLayout = findViewById(R.id.no_objective)

                 if(goals.isEmpty()){
                     containerInfo.visibility = View.GONE
                     goalRecyclerView.visibility = View.GONE
                     containerGoalsTitleAndSort.visibility = View.GONE
                     noObjective.visibility = View.VISIBLE


                 }

                 val adapter =  MainAdapter(goals,this)

                 goalRecyclerView.adapter = adapter
                 goalRecyclerView.layoutManager = LinearLayoutManager(this)
             }

         }.start()






        val btnAddNewGoal : Button = findViewById(R.id.button)
        btnAddNewGoal.setText(R.string.add_new_goal)
        btnAddNewGoal.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.add_icon), null, null, null)

        btnAddNewGoal.setOnClickListener {
            val i = Intent(this, ChooseGoalActivity::class.java)
            startActivity(i)
        }

    }




    private inner class  MainAdapter(private val goals:List<Goal>,
                                     private val onClickListener: OnClickListener<Goal>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.goal_item_activity_main,parent,false)
            return MainViewHolder(view)

        }

        override fun getItemCount(): Int {
            return goals.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = goals[position]
            holder.bind(itemCurrent)
        }

        private inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view){
            fun bind(goal: Goal){


                val goalImg: ImageView = itemView.findViewById(R.id.goal_image)
                goalImg.setImageResource(goal.imgGoal)
                goalImg.scaleType = ImageView.ScaleType.CENTER_CROP

                val goalTitle: TextView = itemView.findViewById(R.id.goal_title)
                goalTitle.text = goal.goalTitle

                val goalSavedValue: TextView = itemView.findViewById(R.id.txt_goal_saved_value)

                val formattedNumber = NumberFormat.getCurrencyInstance(Locale("pt","BR")).format(goal.currentValue)
                goalSavedValue.text = formattedNumber


                val containerGoalItem : ConstraintLayout = itemView.findViewById(R.id.container_goal_item)


                containerGoalItem.setOnClickListener{

                     onClickListener.onClick(goal)
                }

                val saving: ImageButton = itemView.findViewById(R.id.saving)

                saving.setOnClickListener{
                    openSavingActivity(goal.currentValue,goal.id)
                  
                }




            }

        }

    }

    private fun openSavingActivity(value: Double,id:Int){
        val i = Intent(this, SavingsActivity::class.java)
        i.putExtra("currentValue",value)
        i.putExtra("goalId",id)
        startActivity(i)
    }




    override fun onClick(item: Goal) {


        val i = Intent(this, EditGoalActivity::class.java)

        i.putExtra("goalId",item.id)
        i.putExtra("goalTitle",item.goalTitle)
        i.putExtra("goalImg",item.imgGoal)
        i.putExtra("goalValue",item.goalValue)
        i.putExtra("currentValue",item.currentValue)
        startActivity(i)

    }

    override fun onResume() {
        super.onResume()


        Thread{


            val app = application as App
            val dao = app.database.GoalDao()

            goals = dao.getAll()

            var count = 0.0
            for(goal in goals){

                count += goal.currentValue
            }

            Log.i("Teste", "Main Activity Resume")

            runOnUiThread{

                val valueSavedTotalFormatted = formatDoubleToBRL(count)
                valueSavedTotalTextView.text = valueSavedTotalFormatted

                goalRecyclerView = findViewById(R.id.goals_recycler_view)

                val adapter =  MainAdapter(goals,this)

                goalRecyclerView.adapter = adapter
            }

        }.start()
}

    fun clickHelpButton(view: View) {
        showHelpActivity(this)
    }
}