package com.pixelperfectsoft.tcg_nexus.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CollectionViewModel : ViewModel(){
    val collection = mutableStateOf(Collect())

    fun updateCollection(cards: List<String>){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        FirebaseFirestore.getInstance().collection("collections").whereEqualTo("user_id",userId).get().addOnSuccessListener {
            for (document in it.documents) {
                // Actualiza cada documento encontrado con los nuevos datos
                FirebaseFirestore.getInstance().collection("collections").document(document.id)
                    .update("cards",cards)
                    .addOnSuccessListener {
                        Log.d("collection_update","Cards updated successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.w("collection_update","Error updating cards -> $e")
                    }
            }
        }
    }
    fun createCollection(){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val collection = userId?.let {
            Collect(
                collection_id = "",
                user_id = it,
                cards = mutableListOf()
            ).toMap()
        }
        if (collection != null) {
            FirebaseFirestore.getInstance().collection("collections").add(collection).addOnSuccessListener {
                it.update("collection_id", it.id)
                Log.d("collection", "createCol: Collection ${it.id} created successfully")
            }.addOnFailureListener {
                Log.w("collection", "createCol: Unspected error creating collection $it")
            }
        }
    }
}