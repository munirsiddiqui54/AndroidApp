package com.example.pet.ModelClasses

import java.io.Serializable

class Pet : Serializable {
    private var name:String=""
    private var breed:String=""
    private var age:String=""
    private var color:String=""
    private var reason:String=""
    private var description:String=""
    private var medical:String=""
    private var category:String=""
    private var photo:String=""
    private var owner:String=""
    private var gender:String=""
    private var id:String=""
    constructor()
    constructor(
        id:String,
        name: String,
        breed: String,
        age:String,
        color:String,
        reason:String,
        description:String,
        medical:String,
        category: String,
        owner:String,
        photo:String,
        gender:String
    ) {
        this.id=id
        this.name = name
        this.breed = breed
        this.age=age
        this.description=description
        this.medical=medical
        this.reason=reason
        this.category=category
        this.color=color
        this.owner=owner
        this.photo=photo
        this.gender=gender
    }
    fun getId():String{
        return id
    }
    fun getPhoto(): String {
        return photo
    }
    fun getGender():String{
        return gender
    }
    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getBreed(): String {
        return breed
    }

    fun setBreed(breed: String) {
        this.breed = breed
    }

    fun getAge(): String {
        return age
    }

    fun setAge(age: String) {
        this.age = age
    }

    fun getColor(): String {
        return color
    }

    fun setColor(color: String) {
        this.color = color
    }

    fun getReason(): String {
        return reason
    }

    fun setReason(reason: String) {
        this.reason = reason
    }

    fun getDescription(): String {
        return description
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getMedical(): String {
        return medical
    }

    fun setMedical(medical: String) {
        this.medical = medical
    }

    fun getCategory(): String {
        return category
    }

    fun setCategory(category: String) {
        this.category = category
    }

    fun getOwner(): String {
        return owner
    }

    fun setOwner(owner: String) {
        this.owner = owner
    }


}