package com.uzdev.psdic.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uzdev.psdic.model.Word

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWord(word: Word)

    @Update
    suspend fun updateWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("SELECT * FROM word_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Word>>


    @Query("DELETE FROM word_table")
    fun clearAllData()

    @Query("SELECT * FROM word_table  WHERE engName LIKE :query")
    fun searchDatabase(query: String): LiveData<List<Word>>


}