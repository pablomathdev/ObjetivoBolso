package com.objetivobolso.activity

import com.objetivobolso.model.DefaultGoalItem
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.objetivobolso.activity.listener.OnClickListener
import com.objetivobolso.R
import helpers.showHelpActivity

class ChooseGoalActivity : AppCompatActivity(), OnClickListener<DefaultGoalItem> {

    private lateinit var goalRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_goal)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.arrow_back_1)
        toolbar.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

       val defaultGoalItems = mutableListOf<DefaultGoalItem>()
        defaultGoalItems.add(
            DefaultGoalItem(
                goalId = 1,
                goalTitle = R.string.car,
                goalImg = R.drawable.car_img
            )
        )
        defaultGoalItems.add(
            DefaultGoalItem(
                goalId = 2,
                goalTitle = R.string.marriage,
                goalImg = R.drawable.marriage_img
            )
        )
        defaultGoalItems.add(
            DefaultGoalItem(
                goalId = 3,
                goalTitle = R.string.house,
                goalImg = R.drawable.house_img
            )
        )

        defaultGoalItems.add(
            DefaultGoalItem(
                goalId = 4,
                goalTitle = R.string.travel,
                goalImg = R.drawable.travel_img
            )
        )
        defaultGoalItems.add(
            DefaultGoalItem(
                goalId = 5,
                goalTitle = R.string.just_save,
                goalImg = R.drawable.save_money_img
            )
        )

        defaultGoalItems.add(
            DefaultGoalItem(
                goalId = 6,
                goalTitle = R.string.other_goal,
                goalImg = R.drawable.other_goal
            )
        )



        val adapter = MainAdapter(defaultGoalItems,this)
        goalRecyclerView = findViewById(R.id.goals_recycler_view)
        goalRecyclerView.adapter = adapter
        goalRecyclerView.layoutManager = object : GridLayoutManager(this,3){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

    }

    private inner class MainAdapter(private val defaultGoalItems : List<DefaultGoalItem>,
                                     private val onClickListener: OnClickListener<DefaultGoalItem>
    ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.goal_item_activity_choose_goal,parent,false)
            return MainViewHolder(view)

        }

        override fun getItemCount(): Int {
            return defaultGoalItems.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
              val itemCurrent = defaultGoalItems[position]
            holder.bind(itemCurrent)
        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

            fun bind(item: DefaultGoalItem){
                val imgView: ImageView = itemView.findViewById(R.id.goal_image)
                imgView.setImageResource(item.goalImg)

                val txtView: TextView = itemView.findViewById(R.id.goal_title)
                txtView.setText(item.goalTitle)

                val containerGoalItem : ConstraintLayout = itemView.findViewById(R.id.container_goal_item)

                containerGoalItem.setOnClickListener{
                     onClickListener.onClick(item)

                }
            }

        }

    }

   override fun onClick(item: DefaultGoalItem) {

        val i: Intent?

        if(item.goalId == 6){
                 i = Intent(this, CreateCustomGoalActivity::class.java)
                  startActivity(i)
              }else{

                  i = Intent(this, CreateGoalActivity::class.java)



                  i.putExtra("goalTitle", getString(item.goalTitle))
                  i.putExtra("goalImg",item.goalImg)
                  startActivity(i)
              }




       }
    fun clickHelpButton(view: View) {
        showHelpActivity(this)
    }
    }



