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


class UserDataViewModel : ViewModel() {
    val user = mutableStateOf(User())

    init { //Iniciamos la corrutina de obtener los datos
        get_User()
    }

    private fun get_User() {
        viewModelScope.launch {
            user.value = getUserDataFromFirestore()
        }
    }
}

suspend fun getUserDataFromFirestore(): User {
    val auth =
        FirebaseAuth.getInstance() //Obtenemos la instancia del sistema de autenticacion de Firebase
    val currentuser = auth.currentUser //Obtenemos el usuario actualmente logueado
    val db =
        FirebaseFirestore.getInstance()   //Obtenemos la instasncia del sistema de BDD de Firebase
    var user = User()

    try {
        db.collection("users").get().await()
            .map {//Obtenemos la coleccion perteneciente a usuarios y esperamos a que se obtenga algo desde esa coleccion con await
                val result = it.toObject(User::class.java)
                if (currentuser != null) {
                    if (currentuser.uid == result.user_id) {
                        Log.d("get-user", "User found -> $user")
                        user = result
                    } else {
                        Log.d("get-user", "User id mismatch")
                        Log.d("get-user", "Id founded -> ${result.user_id}")
                        Log.d("get-user", "User founded $result")
                        Log.d("get-user", "Id to found -> ${currentuser.uid}")
                    }
                }
                else{
                    Log.d("get-user", "Current User is null")
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