package com.pixelperfectsoft.tcg_nexus.model.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.pixelperfectsoft.tcg_nexus.model.classes.Card
import com.pixelperfectsoft.tcg_nexus.model.classes.Collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.math.BigDecimal

class CollectionViewModel : ViewModel() {
    var state: MutableState<DataState> = mutableStateOf(DataState.Loading)
    private var searchresult = mutableListOf<Card>()
    val collection = mutableStateOf(Collect())
    private val cards = mutableListOf<Card>()
    private val price = mutableStateOf(BigDecimal(0))
    private val order = mutableStateOf("null")


    fun searchCardsByName(searchinput: String) {
        /*
          * Buscando cartas por nombre:
          * 1. Nos creamos un array de cartas vacio
          * 2. Ponemos el estado de la respuesta en cargando
          * 3. Recorremos el array de cartas cards que es el que obtiene todas las cartas de la coleccion
          * 4. Si el nombre de la carta en minusculas contiene lo que introducimos se va a añadir al nuevo array
          * 5. Ponemos el estado de la respuesta en completado y le pasamos el nuevo array
        */
        searchresult.removeAll(searchresult)
        state.value = DataState.Loading
        for (i in collection.value.cards) {
            if (i.name.toString().lowercase().contains(searchinput.lowercase())) {
                searchresult.add(i)
            }
        }
        state.value = DataState.Success(searchresult)
    }





    fun resetSearch() {
        /*
        * Reiniciando la busqueda:
        * 1. Ponemos el estado en cargando
        * 2. Ponemos el estado en completado y le pasamos el array inicial
         */
        state.value = DataState.Loading
        state.value = DataState.Success(collection.value.cards)
        getCollection()
    }

    fun updateCollection(cards: List<Card>) {
        /*
        * Actualizando la coleccion
        * 1. Obtenemos el id del usuario actualmente logueado
        * 2. Obtenemos los documentos que contengan el atributo user_id coincidente con el id del usuario
        * 3. En cada documento que encuentre, actualizamos el atributo "cards" con el array de
        *    String que le pasaremos como parametro
        * 4. Mostramos logs tanto de finalizacion correcta como de error en cada caso
         */
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        FirebaseFirestore.getInstance().collection("collections").whereEqualTo("user_id", userId)
            .get().addOnSuccessListener {
                for (document in it.documents) {
                    // Actualiza cada documento encontrado con los nuevos datos
                    FirebaseFirestore.getInstance().collection("collections").document(document.id)
                        .update("cards", cards)
                        .addOnSuccessListener {
                            Log.d("collection_update", "Cards updated successfully")
                        }
                        .addOnFailureListener { e ->
                            Log.w("collection_update", "Error updating cards -> $e")
                        }
                }
            }
    }

    fun createCollection() {
        /*
        * Creando la coleccion:
        * 1. Obtenemos el id del usuario actualmente logueado
        * 2. Nos creamos un objeto de la clase coleccion con el id del usuario y un array de cartas
        *    vacio. En este array se van a almacenar los id de las cartas en la otra base de datos
        * 3. Obtenemos la coleccion de colecciones y añadimos un documento con el objeto
        *    que nos acabamos de crear
        * 4. Si se crea correctamente, actualizamos el atributo id de la coleccion con el id del
        *    usuario y mostramos el log por consola
        * 5. Si hay algun error al crearlo mostramos el log correspondiente al error por consola
         */
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val collection = userId?.let {
            Collect(
                collection_id = "",
                user_id = it,
                cards = mutableListOf()
            ).toMap()
        }
        if (collection != null) {
            FirebaseFirestore.getInstance().collection("collections").add(collection)
                .addOnSuccessListener {
                    it.update("collection_id", it.id)
                    Log.d("collection", "createCol: Collection ${it.id} created successfully")
                }.addOnFailureListener {
                    Log.w("collection", "createCol: Unspected error creating collection $it")
                }
        }
    }

    init {
        getCollection()
    }

    private fun getCollection() {
        viewModelScope.launch {
            collection.value = retrieveCollection()

        }.invokeOnCompletion { showCardsFromCollection() }


    }

    fun deleteCardFromCollection(card: Card) {
        /*
        * 1. LLamamos al método delete con la carta que queremos borrar como argumento
        * 2. LLamamos al método getCollection para actualizar la lista de las cartas*/
        delete(card)
        getCollection()
    }

    private suspend fun retrieveCollection(): Collect {
        /*
         * Obteniendo la coleccion del usuario:
         * 1. Nos creamos una coleccion vacia
         * 2. Obtenemos el uid del usuario actualmente logueado
         * 3. Obtenemos la coleccion de colecciones y le decimos que obtenga los documentos que
         *      tengan el atributo "user_id" que coincida con el id del usuario actualmente logueado
         * 4. Parseamos el resultado de la query a un objeto de la clase Collect
         * 5. Si el userId no es nulo, devolvemos el resultado con la variable current_collection
        */
        val currentCollection = mutableStateOf(Collect())
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        try {
            if (order.value != "null") {
                FirebaseFirestore.getInstance()
                    .collection("collections")
                    .whereEqualTo("user_id", userId)
                    .orderBy("cards.${order.value}")
                    .get().await().map {
                        val result = it.toObject(Collect::class.java)
                        if (userId != null) {
                            Log.d("collection-retrieve", "Collection found -> ${collection.value}")
                            currentCollection.value = result
                        }
                    }
            } else {
                FirebaseFirestore.getInstance()
                    .collection("collections")
                    .whereEqualTo("user_id", userId)
                    .get().await().map {
                        val result = it.toObject(Collect::class.java)
                        if (userId != null) {
                            Log.d("collection-retrieve", "Collection found -> ${collection.value}")
                            currentCollection.value = result
                        }
                    }
            }
        } catch (e: FirebaseFirestoreException) {
            Log.w("collection-retrieve", "Error retrieving collection data: $e")
        }
        return currentCollection.value
    }

    private fun showCardsFromCollection() {
        /*
        * Mostrando las cartas de la colección:
        * 1. Limpiamos el array de temporal de cartas por si hubiera restos de alguna query anterior
        * 2. Ponemos el estado de la operación en Loading
        * 3. Recorremos el array perteneciente a la colección y añadimos sus cartas al temporal
        * 4. Ponemos el estado de la operación en completado y le pasamos el array temporal
        * 4. Obtenemos cartas de la Realtime Database de Firestore y las añadimos al array temporal
         */
        val collection = collection.value.cards
        cards.removeAll(cards)
        state.value = DataState.Loading
        for (i in collection) {
            cards.add(i)
        }
        state.value = DataState.Success(cards)
    }
    private fun delete(card: Card) {
        /*
        * Borrando una carta de la coleccion:
        * 1. Obtenemos el id del usuario actualmente logueado
        * 2. Obtenemos el id de la carta a borrar
        * 3. Obtenemos la coleccion de colecciones y le decimos que obtenga los documentos que
        *      tengan el atributo "user_id" que coincida con el id del usuario actualmente logueado
        * 4. Parseamos el resultado de la query a un objeto de la clase Collect
        * 5. Si el userId no es nulo, devolvemos el resultado con la variable current_collection
        * 6. Obtenemos la coleccion de cartas y le decimos que obtenga los documentos que
        *      tengan el atributo "card_id" que coincida con el id de la carta a borrar.
        * 7. Parseamos el resultado de la query a un objeto de la clase Card
        * 8. Eliminamos la carta del array de cartas
         */
        val cardId = card.id
        val collection = this.collection
        for (i in collection.value.cards) {
            if (i.id == cardId) {
                collection.value.cards.remove(i)
                break
            }
        }
        updateCollection(collection.value.cards)
    }
    suspend fun deleteCollection(){
        val auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser
        val db = FirebaseFirestore.getInstance()
        if (currentuser != null) {
            val snapshot = db.collection("collections").whereEqualTo("user_id", currentuser.uid).get().await()
            for (i in snapshot.documents){
                i.reference.delete().addOnFailureListener {
                    Log.e("Delete", "User data delete failed by ${it.message}")
                }
            }

        }
    }
}