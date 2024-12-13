package com.objetivobolso.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.objetivobolso.R
import com.objetivobolso.activity.listener.OnClickListener
import com.objetivobolso.model.Image
import helpers.showHelpActivity

class ChooseImageActivity : AppCompatActivity(), OnClickListener<Image> {

    private lateinit var recyclerView:RecyclerView

    private lateinit var button:Button

    private var selectedPosition : Int = RecyclerView.NO_POSITION


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_image)

        button = findViewById(R.id.button)
        button.setText(R.string.save)
        button.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null)
        button.alpha = 0.5f
        button.isEnabled = false



        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.arrow_back_2)
        toolbar.setBackgroundColor(getColor(android.R.color.transparent))
        toolbar.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
        val imageButton = findViewById<ImageButton>(R.id.help_button)
        imageButton.visibility = View.GONE
        val toolbarTitle:TextView = findViewById(R.id.toolbar_title)
        toolbarTitle.text = ""





        val images = mutableListOf<Image>()
        images.add(
            Image(
                imageId = 1,
                imageResId = R.drawable.gifts_2
            )
        )

        images.add(
            Image(
                imageId = 2,
                imageResId = R.drawable.party
            )
        )

        images.add(
            Image(
                imageId = 3,
                imageResId = R.drawable.gifts
            )
        )

        images.add(
            Image(
                imageId = 4,
                imageResId = R.drawable.merry_christmas
            )
        )

        images.add(
            Image(
                imageId = 5,
                imageResId = R.drawable.ballons
            )
        )

        images.add(
            Image(
                imageId = 6,
                imageResId = R.drawable.books
            )
        )

        images.add(
            Image(
                imageId = 7,
                imageResId = R.drawable.university
            )
        )

        images.add(
            Image(
                imageId = 8,
                imageResId = R.drawable.moneys
            )
        )

        images.add(
            Image(
                imageId = 9,
                imageResId = R.drawable.shoes
            )
        )

        images.add(
            Image(
                imageId = 10,
                imageResId = R.drawable.clothes
            )
        )


        val adapter = ChooseImageAdapter(images,this)
        recyclerView = findViewById(R.id.images_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = null


        recyclerView.layoutManager = object : GridLayoutManager(this,3) {
            override fun canScrollVertically(): Boolean {
                return false
            }

        }
    }

    private inner class ChooseImageAdapter(private val images : List<Image>,
        private val onClickListener: OnClickListener<Image>
    ) : RecyclerView.Adapter<ChooseImageAdapter.ChooseImageViewHolder>(){


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseImageViewHolder {
            val view = layoutInflater.inflate(R.layout.image_item_choose_image_activity,parent,false)
            return ChooseImageViewHolder(view)

        }

        override fun getItemCount(): Int {
            return images.size
        }

        override fun onBindViewHolder(holder:ChooseImageViewHolder, position: Int) {
            val itemCurrent = images[position]
            holder.bind(itemCurrent,position)
        }

        private inner class ChooseImageViewHolder(view: View) : RecyclerView.ViewHolder(view){



            fun bind(img: Image,position: Int){
                val imgView: ImageView = itemView.findViewById(R.id.goal_image)
                imgView.setImageResource(img.imageResId)
                val selectedBorder:ImageView = itemView.findViewById(R.id.selected_border)
                val containerGoalItem : FrameLayout = itemView.findViewById(R.id.container_image)

                if (position == selectedPosition) {
                    selectedBorder.setBackgroundResource(R.drawable.border_pure_orange)
                } else {
                    selectedBorder.background = null
                }

                containerGoalItem.setOnClickListener{


                    if (selectedPosition != position) {     //
                        notifyItemChanged(selectedPosition) // estudar esse código
                        selectedPosition = position        //
                        notifyItemChanged(selectedPosition) //


                    }

                    onClickListener.onClick(img)
                }

            }

        }

    }

    override fun onClick(item: Image) {
        button.isEnabled = true
        button.alpha = 1.0f

        button.setOnClickListener{

            val returnIntent = Intent(this,CreateCustomGoalActivity::class.java)

            returnIntent.putExtra("image", item.imageResId)

            setResult(Activity.RESULT_OK,returnIntent)

            finish()
        }

    }
    fun clickHelpButton(view: View) {
        showHelpActivity(this)
    }
}