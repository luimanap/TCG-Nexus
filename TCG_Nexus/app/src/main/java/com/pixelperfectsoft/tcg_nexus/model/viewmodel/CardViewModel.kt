package com.pixelperfectsoft.tcg_nexus.model.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.collection.emptyLongSet
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.pixelperfectsoft.tcg_nexus.model.classes.Card
import com.pixelperfectsoft.tcg_nexus.model.classes.User
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class CardViewModel(context: Context) : ViewModel() {
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)
    var tempList = mutableListOf<Card>() //Lista de cartas que vamos a devolver posteriormente
    private val allcards = 29237
    private val loaded = mutableStateOf(false)
    private var filter = mutableStateOf("id")
    private var searchkey = mutableStateOf("")

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("CardViewModel", Context.MODE_PRIVATE)
    private val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()

    init {
        loadjson(context)
        //load()
    }
    fun loadData(context : Context): String{
        var jsonStr = ""
        try {
            val stream = context.assets.open("cards.json")
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
    private fun loadjson(context : Context){
        response.value = DataState.Loading
        val jsonString = loadData(context)
        val json = Gson().fromJson(jsonString, Array<Card>::class.java)
        tempList = json.toMutableList()
        response.value = DataState.Success(tempList)
    }

    /*private fun load() {
        /*if (!loaded.value) {
            viewModelScope.launch {
                retrieveData()
            }.invokeOnCompletion {loaded.value = true}
        }*/
        if (tempList.size == 0) {
            //retrieveData()
         //   saveCardsToStorage()
            Log.d("load_cards", "Loaded cards from Firebase and saved to cache")
        } else {

            Log.d("load_cards", "Loaded cards from cache")
        }

    }*/

/*
    private fun retrieveData() {

        /*
        * Obteniendo las cartas:
        * 1. Obtenemos la instancia de la BDD
        * 2. Obtenemos la referencia "cards" dentro de la instancia y le decimos que guarde
        *    el resultado de la consulta en caché
        * 3. Cambiamos el estado de la operación a Loading
        * 4. Limitamos la consulta a la variable (si la variable vale 100, muestra las 100
        *    primeras cartas)
        * 5. Ordenamos los resultados en cuanto a la columna correspondiente a la variable
        *    (si la variable es "name", ordena por la columna con el mismo nombre y si no hay
        *    ninguna columna con ese nombre, no ordena los datos)
        * 6. Creamos un listener para cada valor y por cada uno lo convertimos a objeto de
        *    la clase Card.
        * 7. Si ese objeto no es nulo, y contiene el nombre que indicamos lo añadimos al array de cartas que devolveremos
        * 8. Cambiamos el estado de la operación a Success y le pasamos el array si se ha realizado
        *    correctamente o a Failure y le pasamos el mensaje de error si ha habido algún error
         */
        val db = FirebaseDatabase.getInstance()
        db.getReference("cards").keepSynced(true)
        response.value = DataState.Loading
        //tempList.removeAll(tempList)
        db.getReference("cards")
        db.getReference("cards")
            .orderByChild("name")
            .startAt("a")
            .endAt("b")
            //.orderByChild(filter.value.lowercase())
            //.limitToFirst(10)
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (i in snapshot.children) {
                        val card = i.getValue(Card::class.java)

                        if (card != null) {
                            if (card.name.toString().lowercase()
                                    .contains(searchkey.value.lowercase().trim())
                            ) {
                                //Log.d("card_loader", tempList.size.toString())
                                tempList.add(card)
                            }
                        }
                    }
                    response.value = DataState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataState.Failure(error.message)
                }
            })
    }

 */


    fun orderBy(criteria: String) {
        /*
        * Cambiando el filtro de la consulta
        * 1. Ponemos el valor del filtro en el string que le pasamos, exista o no la columna
        * 2. Ejecutamos la consulta para actualizar
         */
        filter.value = criteria
        Log.d("orderBy", "Ordering by -> $criteria")
        //load()
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
        //response.value = DataState.Loading
        searchkey.value = searchinput
        Log.d("searchCardsByName", "Searching by -> $searchinput")
        //load()
        /*var searchList = mutableListOf<Card>()
        response.value = DataState.Loading
        for (i in tempList) {
            if (i.name.toString().lowercase().contains(searchinput.lowercase())) {
                searchList.add(i)
            }
        }
        response.value = DataState.Success(searchList)*/
    }

    fun resetSearch() {
        response.value = DataState.Loading
        searchkey.value = ""
        Log.d("resetSearch", "Search reset is done")
        //load()
    }
}



