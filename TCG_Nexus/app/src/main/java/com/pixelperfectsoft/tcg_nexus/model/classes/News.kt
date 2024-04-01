package com.pixelperfectsoft.tcg_nexus.model.classes

import android.media.Image

data class News(
    var image : Int,
    var title : String,
    var link : String,
    var provider : String,
    var date: String,
)
