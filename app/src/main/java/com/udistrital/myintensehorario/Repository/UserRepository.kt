package com.udistrital.myintensehorario.Repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.udistrital.myintensehorario.Model.User

interface UserRepository {

    fun singUp(user: User): Task<AuthResult>
    fun logIn(email: String, password: String): Task<AuthResult>
}