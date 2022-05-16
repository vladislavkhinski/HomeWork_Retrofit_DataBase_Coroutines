package com.gmail.vladkhinski.homework_app.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.gmail.vladkhinski.homework_app.databinding.FragmentDetailsBinding
import com.gmail.vladkhinski.homework_app.retrofit.BreakingBadService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val characterRepository by lazy(LazyThreadSafetyMode.NONE) {
        BreakingBadService.provideRepository()
    }


    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentDetailsBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            toolbar.setupWithNavController(findNavController())
            loadCharactersDetails(args.id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun loadCharactersDetails(id: Long) {

        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val character = characterRepository.getCharacterDetails(id)
                    heroAvatar.load(character[0].img)
                    nameDetails.text = "Name: ${character[0].name}"
                    birthday.text = "BirthDay: ${character[0].birthday}"
                    nickname.text = "NickName: ${character[0].nickname}"
                    portrayed.text = "Actor: ${character[0].portrayed}"
                } catch (e: Throwable) {
                    handleErrors(e.message ?: ERROR)
                }
            }
        }
    }

    private fun handleErrors(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT)
            .setAction(android.R.string.ok) {}
            .show()
    }

    companion object {
        private const val ERROR = "Something went Wrong!"
    }
}
