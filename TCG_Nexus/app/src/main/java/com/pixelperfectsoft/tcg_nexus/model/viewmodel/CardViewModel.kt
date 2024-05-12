package com.pixelperfectsoft.tcg_nexus.model.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pixelperfectsoft.tcg_nexus.model.classes.Card
import kotlinx.coroutines.launch
import java.io.IOException


class CardViewModel(val context: Context) : ViewModel() {
    private val gson: Gson = GsonBuilder().setLenient().create()
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)
    var tempList = mutableListOf<Card>() //Lista de cartas que vamos a devolver posteriormente
    private val searchkey = mutableStateOf("")
    var start = 0
    var end = 18

    init {
        load()
    }

    private fun loadData(context: Context): String {
        var jsonStr = ""
        try {
            val stream = context.assets.open("json/cards.json")
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            jsonStr = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return jsonStr
    }

    fun loadjson() {
        Log.d("search", "array length -> ${tempList.size}")
        tempList.removeAll(tempList)
        Log.d("search", "array length -> ${tempList.size}")
        Log.d("search", "searchkey -> ${searchkey.value}")
        response.value = DataState.Loading
        val jsonString = loadData(this.context)
        val json = gson.fromJson(jsonString, Array<Card>::class.java)
        val filteredjson = mutableListOf<Card>()
        for (i in json) {
            if (i.name.toString().contains(searchkey.value.lowercase())) {
                filteredjson.add(i)
            }
        }
        tempList = filteredjson
        val cuttedjson = filteredjson.subList(start, end)
        Log.d("search", "array length -> ${tempList.size}")
        response.value = DataState.Success(cuttedjson)
    }

    private fun load() {
        viewModelScope.launch {
            if (tempList.isEmpty()) {
                loadjson()
            } else {
                val cuttedjson = tempList.subList(start,end)
                response.value = DataState.Success(cuttedjson)
            }
        }
    }


    fun searchCardsByName(searchinput: String) {
        /*
          * Buscando cartas por nombre:
          * 1. Nos creamos un array de cartas vacio
          * 2. Ponemos el estado de la respuesta en cargando
          * 3. Recorremos el array de cartas templist que es el que obtiene todas
          *    las cartas de Firebase
          * 4. Si el nombre de la carta en minúsculas contiene lo que introducimos se va a
          *    añadir al nuevo array
          * 5. Ponemos el estado de la respuesta en completado y le pasamos el nuevo array
        */
        val searchlist = mutableListOf<Card>()
        response.value = DataState.Loading
        for (i in tempList) {
            if (i.name.toString().lowercase().contains(searchinput.lowercase().trim())) {
                searchlist.add(i)
            }
        }
        response.value = DataState.Success(searchlist)
    }

    fun resetSearch(context: Context) {
        response.value = DataState.Loading
        searchkey.value = ""
        Log.d("resetSearch", "Search reset is done")
        load()
    }

    fun prevPage(context: Context) {
        if (start != 0) {
            start -= 18
            end -= 18
            load()
        }
    }

    fun nextPage(context: Context) {
        if (start + 18 < tempList.size) {
            start += 18
            end += 18
            load()
        } else{
            start +=18
            end = tempList.size
            load()
        }
    }
}