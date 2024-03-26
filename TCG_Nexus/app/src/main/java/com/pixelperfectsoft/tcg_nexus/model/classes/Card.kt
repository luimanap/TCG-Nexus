package com.pixelperfectsoft.tcg_nexus.model.classes

class Card(
    var cmc: Any = "",
    var color_identity: Any = "",
    var colors: Any = "",
    var flavor_text: Any = "",
    var id: Any = "",
    var image_uris_normal: Any = "",
    var mana_cost: Any = "",
    var name: Any = "",
    var oracle_id: Any = "",
    var oracle_text: Any = "",
    var power: Any = "",
    var prices_eur: Any = "",
    var prices_eur_foil: Any = "",
    var produced_mana: Any = "",
    var purchase_uris_cardmarket: Any = "",
    var rarity: Any = "",
    var set: Any = "",
    var set_id: Any = "",
    var set_name: Any = "",
    var toughness: Any = "",
    var type_line: Any = "",
    var loyalty: Any = "",
){
    /*fun getrarities(): String {
        when (this.rarity.toString().lowercase()){
            "common" -> return "Común"
            "uncommon" -> return "Infrecuente"
            "rare" -> return "Rara"
            "mythic" -> return "Mítica"
            else -> return ""
        }
    }*/
}