package com.uzdev.psdic.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uzdev.psdic.data.db.WordDatabase
import com.uzdev.psdic.domain.model.Word
import com.uzdev.psdic.domain.repository.WordRepository
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

    fun searchWord(query: String): LiveData<List<Word>> {
        return wordRepository.searchWord(query)
    }

    private val _oldWord = MutableLiveData<Word>()
    val oldWord: LiveData<Word> = _oldWord
    fun findWordById(id: Int) {
        viewModelScope.launch {
            val word = wordRepository.findWordById(id)
            _oldWord.postValue(word)
        }

    }


}