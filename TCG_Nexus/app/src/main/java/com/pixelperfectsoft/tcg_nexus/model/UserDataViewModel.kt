package com.pixelperfectsoft.tcg_nexus.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await



class UserDataViewModel : ViewModel(){
    val data = mutableStateOf(User())

    private fun get_data(){
        viewModelScope.launch {
            data.value = getUserDataFromFirestore()
        }
    }
}
suspend fun getUserDataFromFirestore(): User {
    val auth = FirebaseAuth.getInstance()
    val currentuser = auth.currentUser //Current logged user
    val db = FirebaseFirestore.getInstance()
    var user = User()

    try {
        db.collection("users").get().await().map {
            val result = it.toObject(User::class.java)
            if (currentuser != null) {
                if(result.userId == currentuser.uid){
                    user = result
                }
            }
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("UserInfo", "UserInfo : Error retrieving user data: $e")
    }
    return user
}

/*@Composable
fun getUserDataFromFirestore(): User {
    val auth = FirebaseAuth.getInstance()
    val currentuser = auth.currentUser //Current logged user

    val db = FirebaseFirestore.getInstance()
    val usersCollection = db.collection("users") //Users collection in Firestore

    val userDocument = usersCollection.document(currentuser!!.uid) //Current looged user document in Firestore (found by uid)

    var userData by remember { mutableStateOf(User()) }

    userDocument.get().addOnSuccessListener { documentSnapshot ->
        if (documentSnapshot.exists()) {
            userData = documentSnapshot.toObject(User::class.java) //Convert document to a object of class User
        }
    }.addOnFailureListener { exception ->
        Log.d("UserInfo", "UserInfo : Error loading user data: ${exception.message}")
    }
    return userData
}*/