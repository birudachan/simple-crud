package app.twentyhours.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import app.twentyhours.models.Ingredient
import java.util.UUID

class IngredientViewModel : ViewModel() {
    private val _ingredients = MutableLiveData<List<Ingredient>>(emptyList())
    val ingredients: LiveData<List<Ingredient>> = _ingredients

    private val _isInputDialogShowed = MutableLiveData(false)
    val isInputDialogShowed: LiveData<Boolean> = _isInputDialogShowed

    fun showInputDialog() {
        _isInputDialogShowed.value = true
    }

    fun onInputDialogShowed() {
        _isInputDialogShowed.value = false
    }

    val emptyViewVisibility: LiveData<Int>
        get() {
            return ingredients.map {
                if (it.isEmpty()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }

    val recyclerViewVisibility: LiveData<Int>
        get() {
            return ingredients.map {
                if (it.isEmpty()) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        }

    private fun addIngredient(ingredient: Ingredient) {
        val currentList = _ingredients.value ?: emptyList()
        _ingredients.value = currentList + ingredient
    }

    fun removeIngredient(ingredient: Ingredient) {
        val currentList = _ingredients.value ?: emptyList()
        _ingredients.value = currentList - ingredient
    }

    private fun updateIngredient(id: UUID, name: String) {
        val currentList = _ingredients.value ?: emptyList()
        val index = currentList.indexOfFirst { it.id == id }
        if (index != -1) {
            val updatedIngredient = currentList[index].copy(name = name)
            val newList = currentList.toMutableList()
            newList[index] = updatedIngredient
            _ingredients.value = newList
        }
    }

    fun saveIngredient(id: UUID?, name: String) {
        id?.let {
            updateIngredient(id, name)
        } ?: run {
            addIngredient(Ingredient(name = name))
        }
    }

    fun logCurrentList() {
        val currentList = _ingredients.value ?: emptyList()
        Log.i("IngredientViewModel", "Current list: $currentList")
    }

}