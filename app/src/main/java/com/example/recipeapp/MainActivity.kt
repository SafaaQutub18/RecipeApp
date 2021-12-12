package com.example.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val myAdapter : RecyclerViewAdapter by lazy { RecyclerViewAdapter() }
    var recipeList : ArrayList<Recipe.RecipeItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adapter setting
        binding.recyclerV.adapter = myAdapter
        binding.recyclerV.layoutManager = LinearLayoutManager(this)

        getAPIdata()

        binding.addBtn.setOnClickListener {
            intent = Intent(applicationContext, AddRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getAPIdata() {
        binding.idLoadingPB.isVisible = true

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dojo-recipes.herokuapp.com/")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getRecipe()
        retrofitData.enqueue(object : Callback<ArrayList<Recipe.RecipeItem>> {

            override fun onResponse(call: Call<ArrayList<Recipe.RecipeItem>>, response: Response<ArrayList<Recipe.RecipeItem>>) {

                for(recipe in response.body()!!){
                    recipeList.add(
                        Recipe.RecipeItem(
                            recipe.title,
                            recipe.author,
                            recipe.ingredients,
                            recipe.instructions
                    ))
                    Log.d("safff","userrrrrrrrr" + recipe.author)
                }
                binding.idLoadingPB.isVisible = false
                myAdapter.setRecipesList(recipeList)

            }

            override fun onFailure(call: Call<ArrayList<Recipe.RecipeItem>>, t: Throwable) {
                Log.d("errror", "${t}")
            }
//
        })
    }
}