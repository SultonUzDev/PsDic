package com.uzdev.psdic.presentation.list

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.uzdev.psdic.R
import com.uzdev.psdic.databinding.FragmentListBinding
import com.uzdev.psdic.domain.model.Word
import com.uzdev.psdic.presentation.list.adapter.ListAdapter
import com.uzdev.psdic.presentation.viewmodel.WordViewModel


class ListFragment : Fragment() {
    private lateinit var mWordViewModel: WordViewModel
    private lateinit var listAdapter: ListAdapter
    private lateinit var allData: List<Word>

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = ListAdapter()
        mWordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        setUpRecyclerView()
        initialize()
        setHasOptionsMenu(true)





        listAdapter.setOnItemClickListener(object : ListAdapter.onItemClickListener {
            override fun onItemClick(data: Word) {
                deleteWordFromDatabase(data)
            }
        })
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

    }

    private fun initialize() {

        mWordViewModel.readAllData.observe(viewLifecycleOwner, Observer { words ->
            allData = words
            listAdapter.setData(words)
        })
    }

    private fun setUpRecyclerView() {
        binding.rvWordList.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val divider = DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
        }


    }


    @SuppressLint("NotifyDataSetChanged")
    private fun deleteWordFromDatabase(data: Word) {
        val delete = "Do you want to delete the word "
        val spannableBuilder = SpannableStringBuilder(delete)
        spannableBuilder.setSpan(
            ForegroundColorSpan(Color.BLACK),
            0,
            delete.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val spannable = SpannableStringBuilder(data.engName)
        spannable.setSpan(
            ForegroundColorSpan(Color.RED),
            0, data.engName.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableBuilder.append(spannable)


        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete word")
        builder.setCancelable(false)
        builder.setMessage(spannableBuilder)


        builder.setPositiveButton("Yes") { _, _ ->
            mWordViewModel.deleteWord(data)
            listAdapter.notifyDataSetChanged()
        }
        builder.setNegativeButton("No") { _, _ ->
        }
        val dialog = builder.create()

        dialog.show()

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_and_clear_menu, menu)

        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    filter(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    filter(query)
                }
                return true
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_word_menu -> {
                clearWordFromDatabase()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearWordFromDatabase() {
        if (allData.isNotEmpty()) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(" Do you want to delete all words ")
            builder.setCancelable(false)
            builder.setMessage(" Are you sure you want to delete all the words ?")
            builder.setIcon(R.drawable.ic_baseline_warning_24)

            builder.setPositiveButton("OK") { _, _ ->
                mWordViewModel.clearAllWord()
            }
            builder.setNegativeButton("Cancel") { _, _ ->
            }
            val dialog = builder.create()

            dialog.show()

        } else {
            val snackBar = Snackbar.make(
                requireView(), "You have not added any words yet",
                Snackbar.LENGTH_LONG
            )
            snackBar.setActionTextColor(Color.WHITE)
            val snackBarView = snackBar.view
            snackBarView.setBackgroundColor(Color.BLUE)
            val textView =
                snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
            snackBar.show()
        }
    }


    private fun filter(query: String) {
        val searchQuery = "%$query%"
        mWordViewModel.searchWord(searchQuery).observe(this) { list ->

            list.let {
                listAdapter.setData(it)
            }
        }
    }

}