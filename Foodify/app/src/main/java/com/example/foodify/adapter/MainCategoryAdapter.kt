package com.example.foodify.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodify.R
import com.example.foodify.entities.CategoryItems
import kotlinx.android.synthetic.main.item_rv_main_category.view.*


class MainCategoryAdapter: RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {

    var listener: OnItemClickListener? = null
    var ctx: Context? = null
    var arrMainCategory = ArrayList<CategoryItems>()
    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }


    fun setData(arrData : List<CategoryItems>){
        arrMainCategory - arrData as ArrayList<CategoryItems>
    }

    fun setClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        ctx = parent.context
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_main_category, parent, false))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        holder.itemView.tv_dish_name.text = arrMainCategory[position].strCategory

        Glide.with(ctx!!).load(arrMainCategory[position].strCategoryThumb).into(holder.itemView.img_dish)

        holder.itemView.rootView.setOnClickListener{
            listener!!.onClicked(arrMainCategory[position].strCategory)
        }
    }

    override fun getItemCount(): Int {
        return arrMainCategory.size

    }

    interface  OnItemClickListener{
        fun onClicked(categoryName:String)
    }

}