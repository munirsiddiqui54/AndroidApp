package com.example.pet.ModelClasses

class ChatItem (
    private var id:String="",
    private var photo:String="",
    private var name:String="",
    private var seen:Boolean=false,
    private var lastMsg:String=""
){


    fun getId(): String {
        return id
    }

    fun setId(value: String) {
        id = value
    }

    fun getPhoto(): String {
        return photo
    }

    fun setPhoto(value: String) {
        photo = value
    }

    fun getName(): String {
        return name
    }

    fun setName(value: String) {
        name = value
    }

    fun isSeen(): Boolean {
        return seen
    }

    fun setSeen(value: Boolean) {
         this.seen = value
    }

    fun getLastMsg(): String {
        return lastMsg
    }

    fun setLastMsg(value: String) {
        lastMsg = value
    }
}