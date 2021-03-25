package com.uzdev.psdic.fragments.add

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uzdev.psdic.R
import com.uzdev.psdic.databinding.FragmentAddBinding
import com.uzdev.psdic.model.Word
import com.uzdev.psdic.viewmodel.WordViewModel

import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment(R.layout.fragment_add) {
    private lateinit var mWordViewModel: WordViewModel
    private lateinit var binding: FragmentAddBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)
        mWordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        binding.addBtnAt.setOnClickListener {
            insertDataToDatabase()
        }

    }


    private fun insertDataToDatabase() {
        val engName = binding.engNameAt.text.toString()
        val uzbName = binding.uzbNameAt.text.toString()

        if (engName.isEmpty() || uzbName.isEmpty()) {
            if (engName.isEmpty()) {
                binding.engNameAt.error = "Fill fields please"
            } else {
                binding.uzbNameAt.error = "Bu maydonni to'ldiring"
            }

        } else {
            val word = Word(0, engName, uzbName)
            mWordViewModel.addWord(word)
            closeKeyBoard()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
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