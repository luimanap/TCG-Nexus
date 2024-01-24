package com.pixelperfectsoft.tcg_nexus.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreKtxRegistrar
import com.pixelperfectsoft.tcg_nexus.home.HomeScreen
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.concurrent.timerTask

class LoginScreenViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signIn(email: String, password: String, profile: () -> Unit) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("login", "signIn: successful")
                    profile()
                } else {
                    Log.d("login", "signIn: ${task.result}")
                }
            }
        } catch (e: Exception) {
            Log.d("login", "signIn: ${e.message}")
        }
    }
    fun createUserAccount(user: String, email: String, password: String, profile: () -> Unit){
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email.trim(), password.trim()).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d("createuser", "createUser: User created successfully")
                    createUser(user)
                    profile()
                }else{
                    Log.d("createuser","createuser : ${task.result}")
                }
                _loading.value = false
            }
        }
    }

    fun createUser(displayname: String){
        val userId = auth.currentUser?.uid
        val user = mutableMapOf<String, Any>()

        user["user_id"]= userId.toString()
        user["display_name"]= displayname
        FirebaseFirestore.getInstance().collection("users").add(user).addOnSuccessListener {
            Log.d("users", "createUser: Display name ${it.id} created successfully")
        }.addOnFailureListener{
            Log.d("users", "createUser: Unspected error creating user $it")
        }
    }
}