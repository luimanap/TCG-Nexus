package com.pixelperfectsoft.tcg_nexus.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginScreenViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signIn(email: String, password: String, profile: () -> Unit, onError: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    Log.d("login", "signIn: successful")
                    profile()
                }.addOnFailureListener {
                    onError()
                }
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Log.d("login", "signIn: ${e.message}")
            }
        }

    fun createUserAccount(
        displayname: String,
        email: String,
        password: String,
        profile: () -> Unit
    ) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email.trim(), password.trim())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("createuser", "createUser: User created successfully")
                        createUser(displayname, email)
                        profile()
                    } else {
                        Log.d("createuser", "createuser : ${task.result}")
                    }
                    _loading.value = false
                }
        }
    }

    private fun createUser(displayname: String?, email: String) {
        val userId = auth.currentUser?.uid
        //val user = mutableMapOf<String, Any>()

        //user["user_id"]= userId.toString()
        //user["display_name"]= displayname.toString()

        val user = User(
            userId = userId.toString(),
            displayName = displayname.toString(),
            avatarUrl = "",
            email = email,
            id = null
        ).toMap()

        FirebaseFirestore.getInstance().collection("users").add(user).addOnSuccessListener {
            Log.d("users", "createUser: Display name ${it.id} created successfully")
        }.addOnFailureListener {
            Log.d("users", "createUser: Unspected error creating user $it")
        }
    }
}