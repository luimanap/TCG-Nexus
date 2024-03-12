package com.pixelperfectsoft.tcg_nexus.model.viewmodel

import com.pixelperfectsoft.tcg_nexus.model.classes.Card

sealed class DataState {
    class Success(val data: MutableList<Card>) : DataState()
    class Failure(val message: String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
}