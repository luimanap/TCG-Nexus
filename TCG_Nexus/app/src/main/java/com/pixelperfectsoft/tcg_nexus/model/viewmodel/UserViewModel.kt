package com.pixelperfectsoft.tcg_nexus.model.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.pixelperfectsoft.tcg_nexus.model.classes.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class UserDataViewModel : ViewModel() {
    val user = mutableStateOf(User())
/*
    init { //Iniciamos la corrutina de obtener los datos
        get_User()
    }*/

    fun get_User() {
        viewModelScope.launch {
            user.value = getUserDataFromFirestore()
        }
    }
}

suspend fun getUserDataFromFirestore(): User {
    val auth = FirebaseAuth.getInstance() //Obtenemos la instancia del sistema de autenticacion de Firebase
    val currentuser = auth.currentUser //Obtenemos el usuario actualmente logueado
    if (currentuser != null) {
        Log.d("get-user", "Current logged User -> ${currentuser.uid}")
    }
    val db = FirebaseFirestore.getInstance()   //Obtenemos la instasncia del sistema de BDD de Firebase
    var user = User()
    try {
        if (currentuser != null) {
            val querySnapshot = db.collection("users").whereEqualTo("user_id", currentuser.uid).get().await()
                //.map {//Obtenemos la coleccion perteneciente a usuarios y esperamos a que se obtenga algo desde esa coleccion con await
                    for (doc in querySnapshot.documents){
                        val result = doc.toObject(User::class.java)
                        Log.d("get-user", "User -> $result")
                        if (result != null) {
                            user = result
                        }
                    }
               // }
        } else {
            Log.d("get-user", "Current User is null")
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