package com.pixelperfectsoft.tcg_nexus.model

class Card(
    private var name: String,
    private var description: String,
    private var type : String,
    private var rarity : String,
    private var price : Float,
    private var image : String
){
    fun get_name(): String {return this.name}
    fun get_description(): String {return this.description}
    fun get_type(): String {return this.type}
    fun get_rarity(): String {return this.rarity}
    fun get_price(): Float {return this.price}
    fun get_image(): String{return this.image}
}