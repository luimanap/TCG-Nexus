package com.pixelperfectsoft.tcg_nexus.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CardViewModel : ViewModel() {
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)
    val tempList =
        mutableListOf<Card>() //Nos creamos una lista temporal de cartas que vamos a devolver posteriormente
    var loaded = false

    init {
        if (!loaded) {
            retrieveData("null")
            loaded = true
        }
    }


    private fun retrieveData(filter: String) {
        var limit = 10000
        val db = FirebaseDatabase.getInstance() //Obtenemos la instancia de la BDD
        //val tempList = mutableListOf<Card>()
        db.getReference("cards").keepSynced(true) //Obtenemos la referencia "cards" y le decimos que guarde los datos que recibe la consulta en la caché
        response.value = DataState.Loading //Cambiamos el valor del estado a Loading
        tempList.removeAll(tempList)
        when (filter.lowercase()) {
            "name" -> {
                db.getReference("cards")
                    .limitToFirst(limit)//Limitamos la consulta a las primeras 9999 cartas
                    .orderByChild("name")//Ordenamos por nombre
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener { //Nos creamos un listener para cada valor individual
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (i in snapshot.children) {
                                val card = i.getValue(Card::class.java)
                                if (card != null) {
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

            "cmc" -> {
                db.getReference("cards")
                    .limitToFirst(limit)//Limitamos la consulta a las primeras 9999 cartas
                    .orderByChild("cmc")//Ordenamos por coste de mana convertido
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener { //Nos creamos un listener para cada valor individual
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (i in snapshot.children) {
                                val card = i.getValue(Card::class.java)
                                if (card != null) {
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

            "color" -> {
                db.getReference("cards")
                    .orderByChild("colors")//Ordenamos por colores
                    .limitToFirst(limit)//Limitamos la consulta a las primeras 9999 cartas
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener { //Nos creamos un listener para cada valor individual
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (i in snapshot.children) {
                                val card = i.getValue(Card::class.java)
                                if (card != null) {
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

            "type" -> {
                db.getReference("cards")
                    .limitToFirst(limit)//Limitamos la consulta a las primeras 9999 cartas
                    .orderByChild("type_line")//Ordenamos por tipo y subtipo
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener { //Nos creamos un listener para cada valor individual
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (i in snapshot.children) {
                                val card = i.getValue(Card::class.java)
                                if (card != null) {
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

            else -> {
                db.getReference("cards")
                    .limitToFirst(limit)//Limitamos la consulta a las primeras 9999 cartas
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener { //Nos creamos un listener para cada valor individual
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (i in snapshot.children) {
                                val card = i.getValue(Card::class.java)
                                if (card != null) {
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
        }


    }

    fun orderBy(cryteria: String) {
        retrieveData(cryteria)
    }

    fun searchCardsByName(searchinput: String) {
        /*
          * Buscando cartas por nombre:
          * 1. Nos creamos un array de cartas vacio
          * 2. Ponemos el estado de la respuesta en cargando
          * 3. Recorremos el array de cartas templist que es el que obtiene todas las cartas de Firebase
          * 4. Si el nombre de la carta en minusculas contiene lo que introducimos se va a añadir al nuevo array
          * 5. Ponemos el estado de la respuesta en completado y le pasamos el nuevo array
        */
        var searchList = mutableListOf<Card>()
        response.value = DataState.Loading
        for (i in tempList) {
            if (i.name.toString().lowercase().contains(searchinput.lowercase())) {
                searchList.add(i)
            }
        }
        response.value = DataState.Success(searchList)

    }

    fun resetSearch() {
        response.value = DataState.Success(tempList)
    }
}