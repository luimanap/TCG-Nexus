package com.pixelperfectsoft.tcg_nexus.model

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await

class StorageConfig (context: Context){
    private val storage = Firebase.storage
    private val storageRef = storage.reference

    fun getStorageRef(): StorageReference{
        return storageRef.child("profile_pics")
    }

    suspend fun getAvatarImages():List<String>{
        val imageUrls = mutableListOf<String>()
        val listResult: ListResult = getStorageRef().listAll().await()
        for (i in listResult.items){
            val url = i.downloadUrl.await().toString()
            imageUrls.add(url)
        }
        return imageUrls
    }
}
