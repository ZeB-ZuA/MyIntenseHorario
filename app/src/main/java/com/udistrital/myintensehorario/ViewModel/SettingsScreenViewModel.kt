package com.udistrital.myintensehorario.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.udistrital.myintensehorario.Model.User
import com.udistrital.myintensehorario.Service.UserService
import kotlinx.coroutines.launch

class SettingsScreenViewModel(private val userService: UserService) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val uid = auth.currentUser?.uid

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    init {
        loadUser()
    }

    private fun loadUser() {
        uid?.let { uid ->
            viewModelScope.launch {
                val foundUser = userService.findUserById(uid)
                _user.value = foundUser
            }
        }
    }

    fun updateName(user: User) {
        userService.updateName(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("Nombre Actualizado")
                loadUser()
            } else {
                println("Error al actualizar el nombre: ${task.exception?.message}")
            }
        }
    }
}
