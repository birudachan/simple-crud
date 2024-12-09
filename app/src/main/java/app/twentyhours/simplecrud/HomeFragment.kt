package app.twentyhours.simplecrud

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.twentyhours.adapter.IngredientListAdapter
import app.twentyhours.models.Ingredient
import app.twentyhours.simplecrud.databinding.FragmentHomeBinding
import app.twentyhours.viewmodel.IngredientViewModel

class HomeFragment : Fragment() {
    private val viewModel: IngredientViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val ingredientListAdapter = IngredientListAdapter { ingredient ->
            showDialogEdit(ingredient)
        }

        binding.recyclerView.adapter = ingredientListAdapter
        viewModel.ingredients.observe(viewLifecycleOwner) {
            ingredientListAdapter.submitList(it)
        }

        viewModel.isInputDialogShowed.observe(viewLifecycleOwner) {
            if (it) {
                showDialogEdit()
                viewModel.onInputDialogShowed()
            }
        }
    }

    private fun showDialogEdit(ingredient: Ingredient? = null) {
        InputDialogFragment(
            initialText = ingredient?.name ?: "",
            onSubmit = { name -> viewModel.saveIngredient(ingredient?.id, name) },
            onDelete = if (ingredient != null) {
                { viewModel.removeIngredient(ingredient) }
            } else {
                null
            }
        ).show(parentFragmentManager, "InputDialog")
    }
}