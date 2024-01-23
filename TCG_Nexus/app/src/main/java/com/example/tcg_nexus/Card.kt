package com.example.tcg_nexus

import android.media.Image

class Card(
    private var name: String,
    private var description: String,
    private var type : String,
    private var subtype : String?,
    private var rarity : String,
    private var price : Float,
    private var image : Int
){
    fun get_name(): String {return this.name}
    fun get_description(): String {return this.description}
    fun get_type(): String {return this.type}
    fun get_subtype(): String? {return this.subtype}
    fun get_rarity(): String {return this.rarity}
    fun get_price(): Float {return this.price}
    fun get_image(): Int{return this.image}
}