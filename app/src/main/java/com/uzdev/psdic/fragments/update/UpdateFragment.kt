package com.uzdev.psdic.fragments.update


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.uzdev.psdic.R
import com.uzdev.psdic.model.Word
import com.uzdev.psdic.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mWordViewModel: WordViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        mWordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        view.update_engName_at.setText(args.currentWord.engName)
        view.update_uzbName_at.setText(args.currentWord.uzbName)

        view.update_btn_at.setOnClickListener {
            updateItem()
        }

        return view
    }

    private fun updateItem() {
        val engName = update_engName_at.text.toString()
        val uzbName = update_uzbName_at.text.toString()

        if (engName.isEmpty() || uzbName.isEmpty()) {
            if (engName.isEmpty()) {
                update_engName_at.error = "Fill fields please"
            } else {
                update_uzbName_at.error = "Bu maydoni to'ldiring"
            }

        } else {
            val updateWord =
                Word(args.currentWord.id, engName, uzbName)
            mWordViewModel.updateWord(updateWord)
            closeKeyBoard()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
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