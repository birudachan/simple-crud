package app.twentyhours.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.twentyhours.models.Ingredient
import java.util.UUID

class IngredientViewModel : ViewModel() {
    private val _ingredients = MutableLiveData<List<Ingredient>>()
    val ingredients: LiveData<List<Ingredient>> = _ingredients

    fun addIngredient(ingredient: Ingredient) {
        val currentList = _ingredients.value ?: emptyList()
        _ingredients.value = currentList + ingredient
    }

    fun removeIngredient(ingredient: Ingredient) {
        val currentList = _ingredients.value ?: emptyList()
        _ingredients.value = currentList - ingredient
    }

    fun updateIngredient(id: UUID, name: String) {
        val currentList = _ingredients.value ?: emptyList()
        val index = currentList.indexOfFirst { it.id == id }
        if (index != -1) {
            val updatedIngredient = currentList[index].copy(name = name)
            val newList = currentList.toMutableList()
            newList[index] = updatedIngredient
            _ingredients.value = newList
        }
    }

    fun logCurrentList() {
        val currentList = _ingredients.value ?: emptyList()
        Log.i("IngredientViewModel", "Current list: $currentList")
    }

}