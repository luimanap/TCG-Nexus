package com.pixelperfectsoft.tcg_nexus.model.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pixelperfectsoft.tcg_nexus.model.classes.Card


class CardViewModel(context: Context) : ViewModel() {
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)
    val tempList = mutableListOf<Card>() //Lista de cartas que vamos a devolver posteriormente
    private val allcards = 29237
    private val loaded = mutableStateOf(false)
    private var filter = mutableStateOf("id")
    private var searchkey = mutableStateOf("")

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("CardViewModel", Context.MODE_PRIVATE)
    private val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()

    init {
        load()
    }

    private fun load(){
        if (tempList.size==0) {
            retrieveData()
            saveCardsToStorage()
            Log.d("load_cards", "Loaded cards from Firebase and saved to cache")
        }else{
            retrieveCardsFromSharedPreferences()
            Log.d("load_cards", "Loaded cards from cache")
        }

    }

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
        tempList.clear()
        db.getReference("cards")
            //.orderByKey()
            //.startAt(100.0)
            //.endAt(200.0)
            .orderByChild(filter.value.lowercase())
            .limitToFirst(allcards)
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (i in snapshot.children) {
                        val card = i.getValue(Card::class.java)
                        if (card != null) {
                            if (card.name.toString().lowercase()
                                    .contains(searchkey.value.lowercase().trim())
                            ) {
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

    private fun saveCardsToStorage() {
        /*
            Guardando las cartas en el almacenamiento:
            1. Serializamos las cartas
            2. Guardamos las cartas en SharedPreferences
         */
        val serializedCards = tempList.joinToString(";") { it.serialize() }
        sharedPreferencesEditor.putString("cards", serializedCards).apply()
    }

    private fun retrieveCardsFromSharedPreferences() {
        /*
            Recuperando las cartas desde el almacenamiento:
            1. Recuperamos las cartas desde SharedPreferences
            2. Deserializamos las cartas
            3. Añadimos las cartas a la colección
            4. Cambiamos el estado de la operación a Success y
                le pasamos el array si se ha realizado correctamente
         */
        response.value = DataState.Loading
        val serializedCards = sharedPreferences.getString("cards", null)
        serializedCards?.let {
            tempList.clear()
            val cards = it.split(";")
            cards.forEach { serializedCard ->
                tempList.add(Card.deserialize(serializedCard))
            }
            response.value = DataState.Success(tempList)
        }
    }


    fun orderBy(criteria: String) {
        /*
        * Cambiando el filtro de la consulta
        * 1. Ponemos el valor del filtro en el string que le pasamos, exista o no la columna
        * 2. Ejecutamos la consulta para actualizar
         */
        filter.value = criteria
        load()
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
        response.value = DataState.Loading
        searchkey.value = searchinput
        load()
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
        load()
    }
}



