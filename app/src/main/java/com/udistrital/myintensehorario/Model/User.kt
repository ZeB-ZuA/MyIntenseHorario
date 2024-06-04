package com.udistrital.myintensehorario.Model

data class User(
    var uid: String= "",
    var name: String= "",
    var email: String= "",
    var password: String= "",
    var fcmToken: String = ""

){
}
