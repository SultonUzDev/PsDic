package com.uzdev.psdic.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.uzdev.psdic.data.WordDatabase
import com.uzdev.psdic.model.Word
import com.uzdev.psdic.repository.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Word>>
    private val wordRepository: WordRepository

    init {
        val wordDao = WordDatabase.getDatabaseInstance(application).wordDao()
        wordRepository = WordRepository(wordDao)
        readAllData = wordRepository.readAllData
    }

    fun addWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            wordRepository.addWord(word)
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            wordRepository.updateWord(word)
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            wordRepository.deleteWord(word)
        }
    }

    fun clearAllWord() {
        viewModelScope.launch(Dispatchers.IO) {
            wordRepository.clearAllData()
        }
    }

    fun searchDatabase(query: String): LiveData<List<Word>> {
        return wordRepository.searchDatabase(query)
    }


}