package com.pixelperfectsoft.tcg_nexus.model.classes

data class User(
    val avatarUrl : String = "",
    val displayName: String = "",
    val email: String = "",
    val userId: String = ""
){
    fun toMap(): MutableMap<String,Any>{
        return mutableMapOf(
            "avatar_url" to this.avatarUrl,
            "display_name" to this.displayName,
            "email" to this.email,
            "user_id" to this.userId
        )
    }
}
