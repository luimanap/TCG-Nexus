package com.pixelperfectsoft.tcg_nexus.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CardViewModel : ViewModel(){
    val response : MutableState<DataState> = mutableStateOf(DataState.Empty)
    var loaded = false

    init {
        if (!loaded){
            retrieveData()
            loaded = true
        }
    }


    private fun retrieveData() {
        val db = FirebaseDatabase.getInstance() //Obtenemos la instancia de la BDD
        val tempList = mutableListOf<Card>() //Nos creamos una lista temporal de cartas que vamos a devolver posteriormente
        db.getReference("cards").keepSynced(true) //Obtenemos la referencia "cards" y le decimos que guarde los datos que recibe la consulta en la caché
        response.value = DataState.Loading //Cambiamos el valor del estado a Loading
        db.getReference("cards")

            .limitToFirst(100)//Limitamos la consulta a las primeras 9999 cartas
            .addListenerForSingleValueEvent(object : ValueEventListener{ //Nos creamos un listener para cada valor individual
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children){
                    val cardItem = i.getValue(Card::class.java)
                    if(cardItem!=null){
                        tempList.add(cardItem)
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