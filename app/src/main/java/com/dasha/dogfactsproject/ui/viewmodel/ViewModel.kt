package com.dasha.dogfactsproject.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.dasha.dogfactsproject.data.database.FactsDao
import com.dasha.dogfactsproject.data.database.FactsDatabase
import com.dasha.dogfactsproject.data.internet.DogsFactsApi
import com.dasha.dogfactsproject.data.internet.RetrofitClient
import com.dasha.dogfactsproject.data.model.Fact
import com.dasha.dogfactsproject.data.model.Facts
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.create


class ViewModel : ViewModel() {

    lateinit var database: FactsDatabase
    val dogFactsApi = RetrofitClient().getInstance().create(DogsFactsApi::class.java)

    suspend fun getAllSavedFacts(context: Context): List<String> {
        val dao = initializeDao(context)
        var result : List<String> = emptyList()
        GlobalScope.launch {
            result = dao.getAll()
        }.join()
        return result
    }

     suspend  fun getRandomFactsFromInternet(context: Context, factsAmount: Int): List<String> {
        val dao = initializeDao(context)
        var result : List<String> = emptyList()

        GlobalScope.launch {
            val requestResult = dogFactsApi.getSomeFacts(factsAmount).body()
            if(requestResult!!.status){
                result = requestResult.facts
                result.forEach{ fact ->
                    try {
                        dao.save(Fact(fact))
                    }catch (exception : Exception){
                        // fact wasn't saved because it is already in database
                    }
                }
            }
        }.join()
        return result
    }

    suspend fun getRandomFactsFromDatabase(context: Context, factsAmount: Int) :List<String> {
        val dao = initializeDao(context)
        var result : List<String> = emptyList()
        GlobalScope.launch {
            result = dao.getRandomFacts(factsAmount)
        }.join()
       return result
    }

    suspend fun countFactsInDatabase(context : Context) :Int{
        val dao = initializeDao(context)
        var counted = 0
        GlobalScope.launch {
           counted = dao.count()
        }.join()
        println("counted $counted")
        return counted
    }

    suspend fun deleteFactFromDatabase(context: Context, fact : String ) {
        val dao = initializeDao(context)
        GlobalScope.launch {
            dao.deleteFact(Fact(fact))
        }.join()

    }

    private fun initializeDao(context:Context) : FactsDao{
        database = FactsDatabase.getInstance(context)
        return database.factsDao()
    }
}