package com.example.foodify

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodify.adapter.MainCategoryAdapter
import com.example.foodify.adapter.SubCategoryAdapter
import com.example.foodify.database.RecipeDatabase
import com.example.foodify.entities.CategoryItems
import com.example.foodify.entities.MealsItems
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.*



class HomeActivity : BaseActivity() {

    var arrMainCategory = ArrayList<CategoryItems>()
    var arrSubCategory = ArrayList<MealsItems>()

    var mainCategoryAdapter = MainCategoryAdapter()
    var subCategoryAdapter = SubCategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getDataFromDb()

        mainCategoryAdapter.setClickListener(onClicked)
        subCategoryAdapter.setClickListener(onClickedSubItem)



    }

    private val onClicked = object : MainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
           getMealDataFromDb(categoryName)
        }

    }

    private val onClickedSubItem = object : SubCategoryAdapter.OnItemClickListener{
        override fun onClicked(id: String) {
            var intent = Intent(this@HomeActivity, DetailActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }

    }

    private fun getDataFromDb(){
        launch {
            this.let{
              var cat = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getAllCategory()
                arrMainCategory = cat as ArrayList<CategoryItems>
                arrMainCategory.reverse()

                getMealDataFromDb(arrMainCategory[0].strCategory)
                mainCategoryAdapter.setData(arrMainCategory)

                val rvMainCategory = findViewById<RecyclerView>(R.id.rv_main_category)
                rvMainCategory.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                rvMainCategory.adapter = mainCategoryAdapter
            }
        }
    }

    private fun getMealDataFromDb(categoryName:String){
        tvCategory.text = "$categoryName Category"
        launch {
            this.let{
                var cat = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getSpecificMealList(categoryName)
                arrSubCategory = cat as ArrayList<MealsItems>

                subCategoryAdapter.setData(arrSubCategory)

                val rvSubCategory = findViewById<RecyclerView>(R.id.rv_sub_category)
                rvSubCategory.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                rvSubCategory.adapter = subCategoryAdapter
            }
        }
    }
}