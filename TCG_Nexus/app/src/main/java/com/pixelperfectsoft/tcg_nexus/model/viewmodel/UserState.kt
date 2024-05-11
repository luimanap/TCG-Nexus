package com.pixelperfectsoft.tcg_nexus.model.viewmodel

import com.pixelperfectsoft.tcg_nexus.model.classes.User

sealed class UserState {
    class Success(val data: User) : UserState()
    class Failure(val message: String) : UserState()
    object Loading : UserState()
    object Empty : UserState()
}