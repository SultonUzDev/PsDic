package com.uzdev.psdic.presentation.fragments.update


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.uzdev.psdic.R
import com.uzdev.psdic.databinding.FragmentUpdateBinding
import com.uzdev.psdic.domain.model.Word
import com.uzdev.psdic.presentation.viewmodel.WordViewModel

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WordViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        initialize()


    }

    private fun initialize() {
        val id = args.id
        var engName = ""
        var uzbName = ""

        viewModel.findWordById(id)
        viewModel.oldWord.observe(viewLifecycleOwner) { word: Word ->
            engName = word.engName
            uzbName = word.uzbName

            binding.apply {
                etEngName.setText(engName)
                etUzbName.setText(uzbName)
            }

        }




        with(binding) {


            btnEdit.setOnClickListener {
                updateItem(id)
            }
        }
    }

    private fun updateItem(id: Int) {

        with(binding) {
            val engName = etEngName.text.toString()
            val uzbName = etUzbName.text.toString()

            if (engName.isEmpty() || uzbName.isEmpty()) {
                if (engName.isEmpty()) {
                    etEngName.error = "Fill fields please"
                } else {
                    etUzbName.error = "Bu maydonni txo'ldiring"
                }

            } else {
                val updateWord =
                    Word(id, engName, uzbName)
                viewModel.updateWord(updateWord)
                closeKeyBoard()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
        }


    }

    private fun closeKeyBoard() {
        val view = requireActivity().currentFocus
        if (view != null) {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}