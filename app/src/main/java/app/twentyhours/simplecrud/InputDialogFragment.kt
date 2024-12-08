package app.twentyhours.simplecrud

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import app.twentyhours.simplecrud.databinding.DialogIngredientEditBinding

class InputDialogFragment(
    private val initialText: String = "",
    private val onSubmit: (String) -> Unit,
    private val onDelete: (() -> Unit)? = null
) : DialogFragment() {
    private lateinit var _binding: DialogIngredientEditBinding

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, // Width (match parent for fullscreen or custom size)
            ViewGroup.LayoutParams.WRAP_CONTENT  // Height (wrap content or custom size)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogIngredientEditBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.editText.setText(initialText)

        _binding.confirmButton.setOnClickListener {
            val text = _binding.editText.text.toString()
            if (text.isNotBlank()) {
                onSubmit(text)
                dismiss()
            } else {
                _binding.editText.error = "Please enter a name"
            }
        }

        onDelete?.let {
            _binding.deleteButton.visibility = View.VISIBLE
            _binding.deleteButton.setOnClickListener {
            it()
            dismiss()
            }
        }

        _binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }
}