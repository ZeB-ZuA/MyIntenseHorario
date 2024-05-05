package com.udistrital.myintensehorario.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udistrital.myintensehorario.Model.User
import com.udistrital.myintensehorario.Service.UserService

class LoginViewModel(private val userService: UserService? = null) : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email
    private val _pwd = MutableLiveData<String>()
    val pwd: LiveData<String> = _pwd
    private val _signUpEmail = MutableLiveData<String>()
    val signUpEmail: LiveData<String> = _signUpEmail
    private val _signUpName = MutableLiveData<String>()
    val signUpName: LiveData<String> = _signUpName
    private val _signUpPwd = MutableLiveData<String>()
    val signUpPwd: LiveData<String> = _signUpPwd
    private val _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: LiveData<Boolean> = _signUpResult
    private val _logInResult = MutableLiveData<Boolean>()
    val logInResult: LiveData<Boolean> = _logInResult

    fun signUp() {
        val user = User("", _signUpName.value!!, _signUpEmail.value!!, _signUpPwd.value!!)
        userService?.singUp(user)?.addOnCompleteListener { task ->
            _signUpResult.value = task.isSuccessful
        }
    }

    fun logIn(){
        userService?.logIn(_email.value!!,_pwd.value!!)?.addOnCompleteListener{
            task ->
            _logInResult.value = task.isSuccessful
        }
    }

    fun printSignUp() {
        println("Email: ${signUpEmail.value}")
        println("Nombre: ${signUpName.value}")
        println("Contraseña: ${signUpPwd.value}")

    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPwd(pwd: String) {
        _pwd.value = pwd
    }

    fun setSignUpEmail(signUpEmail: String) {
        _signUpEmail.value = signUpEmail
    }

    fun setSignUpName(signUpName: String) {
        _signUpName.value = signUpName
    }

    fun setSignUpPwd(signUpPwd: String) {
        _signUpPwd.value = signUpPwd
    }

    fun signUp(user: User) {
        userService?.singUp(user)
    }


}