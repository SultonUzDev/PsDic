package com.uzdev.psdic.repository

import androidx.lifecycle.LiveData
import com.uzdev.psdic.data.WordDao
import com.uzdev.psdic.model.Word

class WordRepository(private val wordDao: WordDao) {

    val readAllData: LiveData<List<Word>> = wordDao.readAllData()

    fun searchDatabase(query: String): LiveData<List<Word>> {
        return wordDao.searchDatabase(query)
    }

    suspend fun addWord(word: Word) {
        wordDao.addWord(word)
    }

    suspend fun updateWord(word: Word) {
        wordDao.updateWord(word)
    }

    suspend fun deleteWord(word: Word) {
        wordDao.deleteWord(word)
    }

    fun clearAllData() {
        wordDao.clearAllData()

    }


}