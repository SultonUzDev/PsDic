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
import com.uzdev.psdic.databinding.FragmentUpdateBinding
import com.uzdev.psdic.model.Word
import com.uzdev.psdic.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private lateinit var mWordViewModel: WordViewModel
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


        mWordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
      binding.updateEngNameAt.setText(args.currentWord.engName)
      binding.updateUzbNameAt.setText(args.currentWord.uzbName)

      binding.updateBtnAt.setOnClickListener {
            updateItem()
        }


    }
    private fun updateItem() {
        val engName =  binding.updateEngNameAt.text.toString()
        val uzbName = binding.updateUzbNameAt.text.toString()

        if (engName.isEmpty() || uzbName.isEmpty()) {
            if (engName.isEmpty()) {
                binding.updateEngNameAt.error = "Fill fields please"
            } else {
                binding.updateUzbNameAt.error = "Bu maydonni to'ldiring"
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}