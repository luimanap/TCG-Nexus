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


class CardViewModel(context: Context) : ViewModel() {
    private val gson: Gson = GsonBuilder().setLenient().create()

    //private val gson: Gson = Gson
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)
    var tempList = mutableListOf<Card>() //Lista de cartas que vamos a devolver posteriormente
    //private val allcards = 29237
    private val context = context
    private val loaded = mutableStateOf(false)
    private var filter = mutableStateOf("id")
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
                //Log.d("search", "${i.name} contains ${SEARCHKEY.value}")
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
        /*
        viewModelScope.launch {
            loadjson(context)
        }*/
    }

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
/*

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
*/

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



