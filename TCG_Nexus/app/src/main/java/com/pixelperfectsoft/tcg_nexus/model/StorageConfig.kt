package com.pixelperfectsoft.tcg_nexus.model


import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class StorageConfig: ViewModel() {
    var images = mutableStateOf(listOf<String>())
    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private var loaded = false

    init {
        if(!loaded){
            get_images()
            loaded = true
        }

    }
    @Composable
    fun get_context(): Context{
        val context = LocalContext.current
        return context
    }

    private fun get_images() {
        viewModelScope.launch {
            images.value = getAvatarImages()
        }
    }

    fun getStorageRef(): StorageReference {
        return storageRef.child("profile_pics")
    }


    suspend fun getAvatarImages(): List<String> {
        val imageUrls = mutableListOf<String>()
        val listResult: ListResult = getStorageRef().listAll().await()
        for (i in listResult.items) {
                val url = i.downloadUrl.await().toString()
                Log.d("avatar",url)
                imageUrls.add(url)
                //TODO Pendiente por descubrir como pasarle el contexto a glide sin que pete
                //Glide.with(get_context()).load(url).preload()
        }
        return imageUrls
    }
}
