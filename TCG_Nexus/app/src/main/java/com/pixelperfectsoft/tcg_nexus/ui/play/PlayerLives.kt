package com.pixelperfectsoft.tcg_nexus.ui.play

import androidx.compose.runtime.MutableState

data class PlayerLives(
    var p1life: MutableState<Int>,
    var p2life: MutableState<Int>,
    var p3life: MutableState<Int>,
    var p4life: MutableState<Int>,
    //var p5life: MutableState<Int>,
    //var p6life: MutableState<Int>
)
