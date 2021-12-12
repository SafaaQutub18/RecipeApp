package com.example.recipeapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiInterface {
    @GET("/recipes/")
    fun getRecipe(): Call<ArrayList<Recipe.RecipeItem>>

    @POST("/recipes/")
    fun postRecipe(@Body recipeItemData: Recipe.RecipeItem): Call<Recipe.RecipeItem>


}