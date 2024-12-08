package app.twentyhours.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import app.twentyhours.models.Ingredient
import app.twentyhours.simplecrud.databinding.ListItemIngredientBinding

class IngredientListAdapter(val onItemClick: (Ingredient) -> Unit) :
    ListAdapter<Ingredient, ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
                return oldItem == newItem
            }
        }
    }

    class IngredientViewHolder(binding: ListItemIngredientBinding) : ViewHolder(binding.root) {
        val item = binding.item
        val textView: TextView = binding.textView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemIngredientBinding = ListItemIngredientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientViewHolder(listItemIngredientBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is IngredientViewHolder) {
            val item = getItem(position)
            Log.i("IngredientListAdapter", "onBindViewHolder: ${item.name}")
            holder.textView.text = item.name
            holder.item.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}