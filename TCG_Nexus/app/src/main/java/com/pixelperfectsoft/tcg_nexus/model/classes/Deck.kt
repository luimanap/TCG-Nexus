package com.pixelperfectsoft.tcg_nexus.model.classes

class Deck (
    var deck_id : String = "",
    var user_id : String = "",
    var deck_name : String = "",
    var cards : MutableList<String> = mutableListOf(),
    var image_url : String = ""
){
    fun toMap(): MutableMap<String,Any>{
        return mutableMapOf(
            "deck_id" to this.deck_id,
            "user_id" to this.user_id,
            "deck_name" to this.deck_name,
            "cards" to this.cards,
            "image_url" to this.image_url
        )
    }
}

