package com.udistrital.myintensehorario.Repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.udistrital.myintensehorario.Model.User

interface UserRepository {

    fun signUp(user: User): Task<AuthResult>
    fun logIn(email: String, password: String): Task<AuthResult>
    fun googleLogIn(credential: AuthCredential): Task<AuthResult>
    suspend fun findUserById(uid: String): User?

}