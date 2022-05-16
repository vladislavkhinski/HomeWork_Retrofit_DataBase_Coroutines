package com.gmail.vladkhinski.homework_app.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.vladkhinski.homework_app.adapter.CharactersAdapter
import com.gmail.vladkhinski.homework_app.databinding.FragmentListBinding
import com.gmail.vladkhinski.homework_app.retrofit.BreakingBadService
import com.gmail.vladkhinski.homework_app.room.appDatabase
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        CharactersAdapter(requireContext()) { character ->
            findNavController().navigate(
                ListFragmentDirections.toCharacter(character.id)
            )
        }
    }
    private val characterDao by lazy(LazyThreadSafetyMode.NONE) {
        requireContext().appDatabase.characterDao()
    }


    private val characterRepository by lazy(LazyThreadSafetyMode.NONE) {
        BreakingBadService.provideRepository()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentListBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCharactersFromDB()
        loadCharactersToListAndBD()
        with(binding) {
            val layoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager
            swipe.setOnRefreshListener {
                adapter.submitList(emptyList())
                swipe.isRefreshing = false
                loadCharactersToListAndBD()
            }
            recyclerView.addHorizontalSpaceDecoration(SPACE)
        }
    }

    private fun getCharactersFromDB() {
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.submitList(characterDao.getAllCharacters())
        }
    }

    private fun loadCharactersToListAndBD() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val characters = characterRepository.getCharacters()
                characterDao.insertCharacters(characters)
                adapter.submitList(characters)
            } catch (e: Throwable) {
                handleErrors(e.message ?: ERROR)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun RecyclerView.addHorizontalSpaceDecoration(space: Int) {
        addItemDecoration(
            object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State,
                ) {
                    val position = parent.getChildAdapterPosition(view)
                    if (position != 0 && position != parent.adapter?.itemCount) {
                        outRect.top = space
                    }
                }
            }
        )
    }

    private fun handleErrors(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT)
            .setAction(android.R.string.ok) {}
            .show()
    }

    companion object {
        private const val SPACE = 20
        private const val ERROR = "Something went Wrong!"
    }
}

