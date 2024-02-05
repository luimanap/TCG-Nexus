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
    init{
        retrieveData()
    }

    private fun retrieveData() {
        val db = FirebaseDatabase.getInstance()

        val tempList = mutableListOf<Card>()
        response.value = DataState.Loading
        db.getReference("Cards").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(DataSnap in snapshot.children){
                    val cardItem = DataSnap.getValue(Card::class.java)
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