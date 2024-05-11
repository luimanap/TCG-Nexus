package com.pixelperfectsoft.tcg_nexus.model.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
    var state: MutableState<UserState> = mutableStateOf(UserState.Empty)

    fun getUser() {
        viewModelScope.launch {
            getUserDataFromFirestore()
        }
    }


    private suspend fun getUserDataFromFirestore(){
        val auth =
            FirebaseAuth.getInstance() //Obtenemos la instancia del sistema de autenticacion de Firebase
        val currentuser = auth.currentUser //Obtenemos el usuario actualmente logueado
        if (currentuser != null) {
            Log.d("get-user", "Current logged User -> ${currentuser.uid}")
        }
        val db =
            FirebaseFirestore.getInstance()   //Obtenemos la instasncia del sistema de BDD de Firebase
        state.value = UserState.Loading
            try {
                if (currentuser != null) {
                    val querySnapshot =
                        db.collection("users").whereEqualTo("user_id", currentuser.uid).get()
                            .await()
                    for (doc in querySnapshot.documents) {
                        val result = doc.toObject(User::class.java)
                        Log.d("get-user", "User -> $result")
                        if (result != null) {
                            user.value = result
                            state.value = UserState.Success(user.value)
                            break
                        }
                    }
                } else {
                    Log.d("get-user", "Current User is null")
                }

            } catch (e: FirebaseFirestoreException) {
                Log.d("UserInfo", "UserInfo : Error retrieving user data: $e")
            }
    }
}