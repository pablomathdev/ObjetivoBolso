package com.objetivobolso.activity


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.objetivobolso.App
import com.objetivobolso.R
import helpers.addCurrencyFormatter
import helpers.cleanStringAndConvertToDouble
import helpers.formatDoubleToBRL

class SavingsActivity : AppCompatActivity() {

    private lateinit var valueTextView: EditText

    private lateinit var addValuesRecyclerView: RecyclerView

    private lateinit var decreaseValuesRecyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savings)

        val data = intent.extras
        val goalValue = data?.getDouble("currentValue")
        val goalId = data?.getInt("goalId")
        valueTextView = findViewById(R.id.value)

        valueTextView.addCurrencyFormatter { }

        valueTextView.setText(
            formatDoubleToBRL(goalValue!!)

        )

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.close_icon)
        toolbar.setBackgroundColor(getColor(android.R.color.transparent))




        toolbar.setNavigationOnClickListener {


            val currentGoalValue = cleanStringAndConvertToDouble(valueTextView.text.toString())

                Thread {
                    val app = application as App
                    val dao = app.database.GoalDao()
                    dao.update(goalId!!, currentGoalValue)
                }.start()


            finish()


        }

        onBackPressedDispatcher.addCallback(this){
            val currentGoalValue = cleanStringAndConvertToDouble(valueTextView.text.toString())
            println(currentGoalValue)
            Thread {
                val app = application as App
                val dao = app.database.GoalDao()
                dao.update(goalId!!, currentGoalValue)
            }.start()
            finish()
        }


        val valuesList = listOf(5.0, 10.0, 20.0, 50.0, 100.0)

        val adapterAddValues = SavingAddValuesAdapter(valuesList)

        addValuesRecyclerView = findViewById(R.id.add_values_recycler_view)
        addValuesRecyclerView.adapter = adapterAddValues


        val linearLayoutManager1 = LinearLayoutManager(this)
        linearLayoutManager1.orientation = RecyclerView.HORIZONTAL

        addValuesRecyclerView.layoutManager = linearLayoutManager1


        val adapterDecreaseValues = SavingDecreaseValuesAdapter(valuesList)
        decreaseValuesRecyclerView = findViewById(R.id.decrease_values_recycler_view)
        decreaseValuesRecyclerView.adapter = adapterDecreaseValues

        val linearLayoutManager2 = LinearLayoutManager(this)
        linearLayoutManager2.orientation = RecyclerView.HORIZONTAL

        decreaseValuesRecyclerView.layoutManager = linearLayoutManager2


    }

    private inner class SavingAddValuesAdapter(
        private val values: List<Double>
    ) : RecyclerView.Adapter<SavingAddValuesAdapter.SavingAddValuesViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): SavingAddValuesViewHolder {

            val view =
                layoutInflater.inflate(R.layout.button_savings_add_values, parent, false)
            return SavingAddValuesViewHolder(view)
        }

        override fun getItemCount(): Int {
            return values.size
        }

        override fun onBindViewHolder(holder: SavingAddValuesViewHolder, position: Int) {
            val itemCurrent = values[position]
            holder.bind(itemCurrent)
        }

        private inner class SavingAddValuesViewHolder(view: View) : RecyclerView.ViewHolder(view) {


            fun bind(itemCurrent: Double) {
                val textView: TextView = itemView.findViewById(R.id.value)

                @SuppressLint("SetTextI18n")
                textView.text =
                    "+ " + formatDoubleToBRL(itemCurrent)

                val containerValue: ConstraintLayout = itemView.findViewById(R.id.container_value)

                containerValue.setOnClickListener {

                    val valueTextViewText = valueTextView.text.toString()
                    val valueDouble = cleanStringAndConvertToDouble(valueTextViewText)

                    valueTextView.setText(formatDoubleToBRL(valueDouble + itemCurrent))

                }

            }
        }
    }

    private inner class SavingDecreaseValuesAdapter(
        private val values: List<Double>
    ) : RecyclerView.Adapter<SavingDecreaseValuesAdapter.SavingDecreaseValuesViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): SavingDecreaseValuesViewHolder {

            val view =
                layoutInflater.inflate(R.layout.button_savings_decrease_values, parent, false)
            return SavingDecreaseValuesViewHolder(view)
        }

        override fun getItemCount(): Int {
            return values.size
        }

        override fun onBindViewHolder(holder: SavingDecreaseValuesViewHolder, position: Int) {
            val itemCurrent = values[position]
            holder.bind(itemCurrent)
        }

        private inner class SavingDecreaseValuesViewHolder(view: View) :
            RecyclerView.ViewHolder(view) {

            fun bind(itemCurrent: Double) {
                val textView: TextView = itemView.findViewById(R.id.value)

                @SuppressLint("SetTextI18n")
                textView.text =
                    "-  " + formatDoubleToBRL(itemCurrent)

                val containerValue: ConstraintLayout = itemView.findViewById(R.id.container_value)

                containerValue.setOnClickListener {

                    val valueTextViewText = valueTextView.text.toString()
                    val valueDouble = cleanStringAndConvertToDouble(valueTextViewText)


                    val decrease = valueDouble - itemCurrent


                    if (decrease >= 0) {

                        valueTextView.setText(formatDoubleToBRL(decrease))
                    }

                }

            }
        }
    }


}