package com.pixelperfectsoft.tcg_nexus.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class CardViewModel : ViewModel(){
    val response : MutableState<DataState> = mutableStateOf(DataState.Empty)
    init{
        retrieveData()
    }

    private fun retrieveData() {
        val db = FirebaseDatabase.getInstance()
        val tempList = mutableListOf<Card>()
        db.getReference("cards").keepSynced(true)
        response.value = DataState.Loading
        db.getReference("cards").addListenerForSingleValueEvent(object : ValueEventListener{
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