package com.example.recipeapp


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.RowRecyclerviewBinding


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
    class RecyclerViewHolder(val binding: RowRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    var recipeList: ArrayList<Recipe.RecipeItem> = ArrayList()

    fun setRecipesList(recipeArrayList: ArrayList<Recipe.RecipeItem>) {
        this.recipeList = recipeArrayList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(RowRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        var currentRecipe = recipeList[position]
        holder.binding.apply {
            recipeInfoTV.text = currentRecipe.title +"\n"+ currentRecipe.author +"\n" + currentRecipe.ingredients
            recipeTV.text = "\n"+currentRecipe.instructions
        }
    }
    override fun getItemCount() = recipeList.size

}
