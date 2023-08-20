package com.uzdev.psdic.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.speech.tts.TextToSpeech
import android.view.*
import android.widget.ImageButton
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.uzdev.psdic.R
import com.uzdev.psdic.databinding.CustomRowBinding
import com.uzdev.psdic.domain.model.Word
import com.uzdev.psdic.presentation.fragments.list.ListFragmentDirections
import java.util.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var wordList = emptyList<Word>()
    private var tts: TextToSpeech? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        parent
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(wordList[position])


    override fun getItemCount() = wordList.size

    fun setData(words: List<Word>) {
        this.wordList = words
        notifyDataSetChanged()
    }

    inner class MyViewHolder(
        private val binding: CustomRowBinding,
        private val parent: ViewGroup
    ) : RecyclerView.ViewHolder(binding.root),
        TextToSpeech.OnInitListener {
        fun bind(data: Word) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                binding.rootLayout.setOnClickListener {
                    showPopupMenu(data, parent.context)
                }
                tts = TextToSpeech(parent.context, this)
                binding.buttonSpeak.setOnClickListener {
                    val text: String = data.engName
                    tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
                }
            }

            binding.engWords.text = sortWordsLetter(data.engName)
            binding.uzbWords.text = sortWordsLetter(data.uzbName)
        }

        @SuppressLint("RestrictedApi")
        private fun showPopupMenu(data: Word, context: Context) {


            val popupMenu = PopupMenu(binding.rootLayout.context, binding.rootLayout)
            popupMenu.apply {
                inflate(R.menu.popub_menu)

                setOnMenuItemClickListener { item ->
                    return@setOnMenuItemClickListener when (item.itemId) {
                        R.id.edit -> {
                            val action =
                                ListFragmentDirections.actionListFragmentToUpdateFragment(data.id)
                            itemView.findNavController().navigate(action)
                            true
                        }

                        R.id.delete -> {
                            mClickItemListener.onItemClick(data)
                            true
                        }

                        else -> false
                    }
                }

            }.show()


            val menuHelper: MenuPopupHelper = MenuPopupHelper(
                binding.rootLayout.context,
                popupMenu.menu as MenuBuilder, binding.rootLayout
            )
            menuHelper.setForceShowIcon(true)
            menuHelper.show()

        }


        override fun onInit(status: Int) {
            if (status == TextToSpeech.SUCCESS) {
                val result = tts!!.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {

                } else {

                }
            }
//        else {
//           // Log.e("TTS", "Initilization Failed!")
//        }
        }
    }

    lateinit var mClickItemListener: onItemClickListener

    fun setOnItemClickListener(aClickListener: onItemClickListener) {
        mClickItemListener = aClickListener
    }

    interface onItemClickListener {
        fun onItemClick(data: Word)
    }

    private fun sortWordsLetter(word: String): String {
        return word[0].toUpperCase() + word.substring(
            1,
            word.length
        ).toLowerCase()
    }


}