package com.dasha.dogfactsproject.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dasha.dogfactsproject.data.model.Fact

@Dao
interface FactsDao {

    @Insert
    fun save(fact : Fact)

    @Insert
    fun saveAll(vararg fact: Fact)

    @Query("SELECT fact FROM Fact")
    fun getAll() : List<String>

    @Query("SELECT count(*) FROM Fact")
    fun count() : Int

    @Query("SELECT fact FROM fact ORDER BY RANDOM() LIMIT :amount")
    fun getRandomFacts(amount : Int) : List<String>

    @Delete
    fun deleteFact(fact: Fact)
}