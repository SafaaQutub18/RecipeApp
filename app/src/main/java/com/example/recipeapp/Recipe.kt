package com.example.recipeapp

class Recipe {
    var recipeData : ArrayList<RecipeItem>? = null

    data class RecipeItem(
        val title: String,
        val author: String,
        val ingredients: String,
        val instructions: String
    )

}