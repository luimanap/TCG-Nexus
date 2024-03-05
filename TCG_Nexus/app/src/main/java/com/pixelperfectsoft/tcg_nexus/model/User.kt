package com.pixelperfectsoft.tcg_nexus.model

data class User(
    val avatar_url : String? = "",
    val display_name: String = "",
    val email: String = "",
    val user_id: String = ""
){
    fun toMap(): MutableMap<String,Any>{
        return mutableMapOf(
            "avatar_url" to this.avatar_url.toString(),
            "display_name" to this.display_name,
            "email" to this.email,
            "user_id" to this.user_id
        )
    }
}
