package com.example.pet.ModelClasses

class User {
    private var uid:String=""
    private var username:String=""
    private var email:String=""
    private var address:String=""
    private var profile:String=""
    private var phone:String=""

    constructor()
    constructor(
        uid: String,
        username: String,
        email: String,
        address: String,
        profile: String,
        phone: String
    ) {
        this.uid = uid
        this.username = username
        this.email = email
        this.address = address
        this.profile = profile
        this.phone = phone
    }

    fun getUid():String{
        return this.uid
    }

    fun setUid(uid:String){
        this.uid=uid
    }
    fun getUsername(): String {
        return this.username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    // Getter and Setter methods for email
    fun getEmail(): String {
        return this.email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    // Getter and Setter methods for address
    fun getAddress(): String {
        return this.address
    }

    fun setAddress(address: String) {
        this.address = address
    }

    // Getter and Setter methods for profile
    fun getProfile(): String {
        return this.profile
    }

    fun setProfile(profile: String) {
        this.profile = profile
    }

    // Getter and Setter methods for phone
    fun getPhone(): String {
        return this.phone
    }

    fun setPhone(phone: String) {
        this.phone = phone
    }
}