package com.pixelperfectsoft.tcg_nexus.model.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.pixelperfectsoft.tcg_nexus.model.classes.User
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
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
                Log.w("login", "signIn: ${e.message}")
            }
        }

    fun createUserAccount(
        displayname: String,
        email: String,
        password: String,
        profile: () -> Unit
    ) {
        val usercol = CollectionViewModel()
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email.trim(), password.trim())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("create_user", "create_user: User created successfully")
                        createUser(displayname, email)
                        usercol.createCollection()
                        profile()
                    } else {
                        Log.d("create_user", "create_user : ${task.result}")
                    }
                    _loading.value = false
                }
        }
    }

    private fun createUser(displayName: String?, email: String) {
        val userId = auth.currentUser?.uid
        val user = User(
            avatar_url = "avatars/noavatar.png",
            display_name = displayName.toString(),
            email = email,
            user_id = userId.toString()
        ).toMap()

        FirebaseFirestore.getInstance().collection("users").add(user).addOnSuccessListener {
            Log.d("users", "createUser: Display name ${it.id} created successfully")
        }.addOnFailureListener {
            Log.d("users", "createUser: Unexpected error creating user $it")
        }

    }
}