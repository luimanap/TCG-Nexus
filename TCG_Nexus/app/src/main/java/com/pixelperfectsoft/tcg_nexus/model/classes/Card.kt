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
    var set_id: Any = "",
    var set_name: Any = "",
    var toughness: Any = "",
    var type_line: Any = "",
    var loyalty: Any = "",
){
    fun serialize(): String {
        return "$cmc|$color_identity|$colors|$flavor_text|$id|" +
                "$image_uris_normal|$mana_cost|$name|$oracle_id|" +
                "$oracle_text|$power|$prices_eur|$prices_eur_foil|" +
                "$produced_mana|$purchase_uris_cardmarket|$rarity|" +
                "$set_id|$set_name|$toughness|$type_line|$loyalty"
    }

    companion object{
        fun deserialize(s: String): Card {
            val split = s.split("|")
            return Card(
                cmc = split[0],
                color_identity = split[1],
                colors = split[2],
                flavor_text = split[3],
                id = split[4],
                image_uris_normal = split[5],
                mana_cost = split[6],
                name = split[7],
                oracle_id = split[8],
                oracle_text = split[9],
                power = split[10],
                prices_eur = split[11],
                prices_eur_foil = split[12],
                produced_mana = split[13],
                purchase_uris_cardmarket = split[14],
                rarity = split[15],
                set_id = split[16],
                set_name = split[17],
                toughness = split[18],
                type_line = split[19],
                loyalty = split[20])
        }
    }
}