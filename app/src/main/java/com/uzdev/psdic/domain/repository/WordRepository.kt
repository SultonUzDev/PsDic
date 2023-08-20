package com.uzdev.psdic.domain.repository

import androidx.lifecycle.LiveData
import com.uzdev.psdic.data.db.WordDao
import com.uzdev.psdic.domain.model.Word

class WordRepository(private val wordDao: WordDao) {

    val readAllData: LiveData<List<Word>> = wordDao.readAllData()

    fun searchWord(query: String): LiveData<List<Word>> {
        return wordDao.searchWord(query)
    }

    fun findWordById(id: Int): Word {
        return wordDao.findWordById(id = id)
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