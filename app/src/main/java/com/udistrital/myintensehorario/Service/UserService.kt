package com.udistrital.myintensehorario.Service

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.udistrital.myintensehorario.Model.User
import com.udistrital.myintensehorario.Repository.UserRepository

class UserService : UserRepository {
    override fun singUp(user: User): Task<AuthResult> {
        val db = Firebase.database
        val ref = db.getReference("users")

        val authTask = FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email, user.password)
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
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
    }


}
