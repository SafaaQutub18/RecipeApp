package com.example.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.recipeapp.databinding.ActivityAddRecipeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddRecipeActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener {
            var title = binding.titleET.text.toString()
            var auther = binding.authorET.text.toString()
            var ingredients = binding.ingredientsET.text.toString()
            var instructions = binding.instructionsET.text.toString()
            

            if (title!= "" || auther != ""|| ingredients!=""|| instructions!="")
                postRecipe(title,auther,ingredients,instructions)
            else
                Toast.makeText(this , "Empty name or location!!", Toast.LENGTH_SHORT).show()
        }

        binding.viewBtn.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun postRecipe(title: String, auther: String, ingredients: String, instructions: String) {
       // binding.loadingPB.visibility = View.VISIBLE;

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dojo-recipes.herokuapp.com")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.postRecipe(Recipe.RecipeItem(title,auther,ingredients,instructions))
        retrofitData.enqueue(object : Callback<Recipe.RecipeItem> {
            override fun onResponse(call: Call<Recipe.RecipeItem>, response: Response<Recipe.RecipeItem>) {
                Toast.makeText(this@AddRecipeActivity, "Recipe added to API", Toast.LENGTH_SHORT).show();
                // below line is for hiding our progress bar.
               // binding.loadingPB.visibility = View.GONE;

                binding.titleET.setText("")
                binding.authorET.setText("");
                binding.ingredientsET.setText("")
                binding.instructionsET.setText("");

                // we are getting response from our body
                // and passing it to our modal class.
                val responseFromAPI: Recipe.RecipeItem? = response.body()
                val responseString = """
                    Response Code : ${response.code()}
                    Name : ${responseFromAPI?.author }
                    """.trimIndent()

                Log.d("Main" , responseString)
            }

            override fun onFailure(call: Call<Recipe.RecipeItem>, t: Throwable) {
                Toast.makeText(this@AddRecipeActivity, "Error: recipe ser not added to API", Toast.LENGTH_LONG)
            }
        })
    }

}