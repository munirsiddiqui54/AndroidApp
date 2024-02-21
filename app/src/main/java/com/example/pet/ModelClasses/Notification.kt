package com.example.pet.ModelClasses

data class Notification(
     var reqKey: String? = null,
    var rId: String? = null,
    var rName: String? = null,
    var rPet: String? = null,
    var rStatus: String? = null,
    var oId: String? = null,
    var rPhoto:String?=null
) {
    // Getters and setters
    fun getKey(): String? {
        return reqKey
    }
    fun getPhoto():String?{
        return rPhoto
    }
    fun setKey(key: String?) {
        reqKey= key
    }

    fun getId(): String? {
        return rId
    }

    fun setId(id: String?) {
        rId = id
    }

    fun getName(): String? {
        return rName
    }

    fun setName(name: String?) {
        rName = name
    }

    fun getPet(): String? {
        return rPet
    }

    fun setPet(pet: String?) {
        rPet = pet
    }

    fun getStatus(): String? {
        return rStatus
    }

    fun setStatus(status: String?) {
        rStatus = status
    }

    fun getOwner(): String? {
        return oId
    }

    fun setOwner(id: String?) {
        oId = id
    }
}
