package com.udistrital.myintensehorario

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging

class MyApp: Application() {



    companion object{
        const val NOTIFICATION_CHANNEL_ID = "notis_intensse"
    }

    override fun onCreate() {
        super.onCreate()
        Firebase.messaging.token.addOnCompleteListener{
            if(!it.isSuccessful){
                println("Token no Generado")
                return@addOnCompleteListener
            }
            val token = it.result
            saveTokenToDatabase(token)
            println("El Token es: ${token}")
        }
        createNotificationChanel()
    }

    private fun createNotificationChanel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Notificaciones Intensas",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Notis desde firebase"
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)

        }
    }
    private fun saveTokenToDatabase(token: String?) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null && token != null) {
            val database = FirebaseDatabase.getInstance()
            val userRef = database.getReference("users").child(userId)
            userRef.child("fcmToken").setValue(token)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        println("Token guardado correctamente en el usuario")
                    } else {
                        println("Error al guardar el token en el usuario: ${task.exception?.message}")
                    }
                }
        } else {
            println("Error: Usuario actual no encontrado o token nulo")
        }
    }

}