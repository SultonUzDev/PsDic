package com.uzdev.psdic.presentation.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uzdev.psdic.R
import com.uzdev.psdic.databinding.FragmentAddBinding
import com.uzdev.psdic.domain.model.Word
import com.uzdev.psdic.presentation.viewmodel.WordViewModel


class AddFragment : Fragment() {

    private lateinit var mWordViewModel: WordViewModel
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        mWordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setOnClickListener {
            insertWordToDatabase()
        }
    }


    private fun insertWordToDatabase() {
        with(binding) {

            val enWord = etEngName.text.toString()
            val uzWord = etYourLang.text.toString()

            if (enWord.isEmpty() || uzWord.isEmpty()) {
                if (enWord.isEmpty()) {
                    etEngName.error = getString(R.string.fill_fields_please)
                } else {
                    etYourLang.error = getString(R.string.fill_fields_please_in_your_lang)
                }

            } else {
                val word = Word(0, enWord, uzWord)
                mWordViewModel.addWord(word)
                closeKeyBoard()
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
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


}