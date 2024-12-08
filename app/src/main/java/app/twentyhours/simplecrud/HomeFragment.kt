package app.twentyhours.simplecrud

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.twentyhours.adapter.IngredientListAdapter
import app.twentyhours.models.Ingredient
import app.twentyhours.simplecrud.databinding.FragmentHomeBinding
import app.twentyhours.viewmodel.IngredientViewModel

class HomeFragment : Fragment() {
    private val _viewModel: IngredientViewModel by viewModels()
    private lateinit var _binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ingredientListAdapter = IngredientListAdapter(onItemClick = { ingredient ->
            InputDialogFragment(ingredient.name,
                onSubmit = { name ->
                    _viewModel.updateIngredient(ingredient.id, name)
                },
                onDelete = {
                    _viewModel.removeIngredient(ingredient)
                }
            )
                .show(parentFragmentManager, "InputDialog")
        })
        _binding.recyclerView.adapter = ingredientListAdapter
        _viewModel.ingredients.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                _binding.recyclerView.visibility = View.GONE
                _binding.emptyView.visibility = View.VISIBLE
                _binding.emptyView.findViewById<TextView>(R.id.text_view).text = "No ingredients"
            } else {
                _binding.recyclerView.visibility = View.VISIBLE
                _binding.emptyView.visibility = View.GONE
                ingredientListAdapter.submitList(it)
            }
        }

        _binding.fab.setOnClickListener {
            InputDialogFragment(onSubmit = {
                _viewModel.addIngredient(Ingredient(name = it))
            })
                .show(parentFragmentManager, "InputDialog")
        }
    }
}