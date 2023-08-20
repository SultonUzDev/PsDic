package com.uzdev.psdic.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uzdev.psdic.domain.model.Word

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWord(word: Word)

    @Update
    suspend fun updateWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("SELECT * FROM words ORDER BY id ASC")
    fun readAllData(): LiveData<List<Word>>


    @Query("DELETE FROM words")
    fun clearAllData()

    @Query("SELECT * FROM words  WHERE engName LIKE :query")
    fun searchWord(query: String): LiveData<List<Word>>

    @Query("SELECT * FROM words WHERE id LIKE :id")
    fun findWordById(id: Int): Word

}