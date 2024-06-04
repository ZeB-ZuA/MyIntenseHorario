package com.udistrital.myintensehorario.Service

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.udistrital.myintensehorario.Model.User
import com.udistrital.myintensehorario.Repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await

class UserService : UserRepository {
    private val db = Firebase.database
    private val ref = db.getReference("users")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun signUp(user: User): Task<AuthResult> {


        val authTask =
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email, user.password)
        authTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val firebaseUser = task.result?.user
                val uid = firebaseUser?.uid
                if (uid != null) {
                    val userMap = mapOf(
                        "uid" to uid,
                        "name" to user.name,
                        "email" to user.email,
                    )
                    ref.child(uid).setValue(userMap)
                }
            } else {
                println("Error: ${task.exception?.message}")
            }
        }
        return authTask
    }

    override fun logIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    override fun googleLogIn(credential: AuthCredential): Task<AuthResult> {
        val authTask = auth.signInWithCredential(credential)
        authTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val firebaseUser = task.result?.user
                val uid = firebaseUser?.uid
                if (uid != null) {
                    val userMap = mapOf(
                        "uid" to uid,
                        "name" to firebaseUser.displayName,
                        "email" to firebaseUser.email,
                    )
                    ref.child(uid).setValue(userMap)
                }
            } else {
                println("Error: ${task.exception?.message}")
            }
        }
        return authTask
    }

    override fun updateName(user: User): Task<Void> {
        val uid = user.uid
        val userMap = mapOf(
            "uid" to uid,
            "name" to user.name,
            "email" to user.email,
        )
        return ref.child(uid).updateChildren(userMap)
    }

    override suspend fun findUserById(uid: String): User? {
        return try {
            val userRef = ref.child(uid)
            val dataSnapshot = userRef.get().await()
            dataSnapshot.getValue(User::class.java)
        } catch (e: Exception) {
            println("Error en findUserById: ${e.message}")
            null
        }
    }


    }



