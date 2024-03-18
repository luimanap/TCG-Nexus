package com.pixelperfectsoft.tcg_nexus.model.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pixelperfectsoft.tcg_nexus.model.classes.Card

class CardViewModel : ViewModel() {
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)
    val tempList = mutableListOf<Card>() //Lista de cartas que vamos a devolver posteriormente
    private val allcards = 29237
    private var loaded = false
    private var filter = mutableStateOf("null")
    private var limit = mutableIntStateOf(allcards)
    private var searchkey = mutableStateOf("")


    init {
        if (!loaded) {
            retrieveData()
            loaded = true
        }
    }


    private fun retrieveData() {
        /*
        * Obteniendo las cartas:
        * 1. Obtenemos la instancia de la BDD
        * 2. Obtenemos la referencia "cards" dentro de la instancia y le decimos que guarde
        *    el resultado de la consulta en caché
        * 3. Cambiamos el estado de la operacion a Loading
        * 4. Limitamos la consulta a la variable (si la variable vale 100, muestra las 100
        *    primeras cartas)
        * 5. Ordenamos los resultados en cuanto a la columna correspondiente a la variable
        *    (si la variable es "name", ordena por la columna con el mismo nombre y si no hay
        *    ninguna columna con ese nombre, no ordena los datos)
        * 6. Creamos un listener para cada valor y por cada uno lo convertimos a objeto de
        *    la clase Card.
        * 7. Si ese objeto no es nulo, lo añadimos al array de cartas que devolveremos
        * 8. Cambiamos el estado de la operacion a Success y le pasamos el array si se ha realizado
        *    correctamente o a Failure y le pasamos el mensaje de error si ha habido algun error
         */
        val db = FirebaseDatabase.getInstance()
        db.getReference("cards").keepSynced(true)
        response.value = DataState.Loading
        tempList.removeAll(tempList)
        db.getReference("cards")
            .orderByChild(filter.value.lowercase())
            .limitToFirst(limit.intValue)
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (i in snapshot.children) {
                        val card = i.getValue(Card::class.java)
                        if (card != null && card.name.toString().lowercase().contains(searchkey.value.lowercase())) {
                            tempList.add(card)
                        }
                    }
                    response.value = DataState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataState.Failure(error.message)
                }
            })
    }


    fun orderBy(criteria: String) {
        /*
        * Cambiando el filtro de la consulta
        * 1. Ponemos el valor del filtro en el string que le pasamos, exista o no la columna
        * 2. Ejecutamos la consulta para actualizar
         */
        filter.value = criteria
        retrieveData()
    }

    fun setLimit(limit: Int) {
        /*
        * Cambiando el limite de la consulta
        * 1. Si el numero que le pasamos al metodo no es 0, ponemos ese numero como limite.
        *    Por otro lado si el numero que le pasamos es 0, vamos a poner el numero
        *    maximo de cartas como limite.
        * 2. Ejecutamos la consulta para actualizar
         */
        if(limit!=-1){
            this.limit.intValue = limit
        }else{
            this.limit.intValue = allcards
        }
        response.value = DataState.Loading
        retrieveData()
    }

    fun searchCardsByName(searchinput: String) {
        /*
          * Buscando cartas por nombre:
          * 1. Nos creamos un array de cartas vacio
          * 2. Ponemos el estado de la respuesta en cargando
          * 3. Recorremos el array de cartas templist que es el que obtiene todas
          *    las cartas de Firebase
          * 4. Si el nombre de la carta en minusculas contiene lo que introducimos se va a
          *    añadir al nuevo array
          * 5. Ponemos el estado de la respuesta en completado y le pasamos el nuevo array
        */
        response.value = DataState.Loading
        searchkey.value = searchinput
        retrieveData()
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
        retrieveData()
    }
}