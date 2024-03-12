package com.pixelperfectsoft.tcg_nexus.model.classes

class Collect (
    var collection_id : String = "",
    var user_id : String = "",
    var cards: MutableList<Card> = mutableListOf()
){
    fun toMap(): MutableMap<String,Any>{
        return mutableMapOf(
            "collection_id" to this.collection_id,
            "user_id" to this.user_id,
            "cards" to this.cards
        )
    }
}