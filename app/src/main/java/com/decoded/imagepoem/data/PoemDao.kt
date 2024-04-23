package com.decoded.imagepoem.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PoemDao {
    @Query("SELECT * FROM poem")
    fun getAll(): List<Poem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPoem(poem: Poem)

    @Update
    suspend fun update(poem: Poem)

    @Delete
    suspend fun delete(poem: Poem)
}