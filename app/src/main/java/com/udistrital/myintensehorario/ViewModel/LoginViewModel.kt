package com.udistrital.myintensehorario.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email
    private val _pwd = MutableLiveData<String>()
    val pwd : LiveData<String> = _pwd
    private val _signUpEmail = MutableLiveData<String>()
    val signUpEmail : LiveData<String> = _signUpEmail
    private val _signUpName = MutableLiveData<String>()
    val signUpName : LiveData<String> = _signUpName
    private val _signUpPwd = MutableLiveData<String>()
    val signUpPwd : LiveData<String> = _signUpPwd
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



}