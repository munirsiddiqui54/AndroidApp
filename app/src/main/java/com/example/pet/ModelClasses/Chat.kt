package com.example.pet.ModelClasses

class Chat (
    private var sender: String = "",
    private var receiver: String = "",
    private var message: String = "",
    private var isSeen: Boolean = false,
    private var url: String = "",
    private var msgId: String = "",
    private var rName:String="",
    private var rPhoto:String=""
){
        // Getters
        fun getSender(): String {
            return sender
        }

    fun getRName(): String {
        return rName
    }
    fun getRPhoto(): String {
        return rPhoto
    }
        fun getReceiver(): String {
            return receiver
        }

        fun getMessage(): String {
            return message
        }

        fun isSeen(): Boolean {
            return isSeen
        }

        fun getUrl(): String {
            return url
        }

        fun getMsgId(): String {
            return msgId
        }

        // Setters
        fun setSender(sender: String) {
            this.sender = sender
        }

        fun setReceiver(receiver: String) {
            this.receiver = receiver
        }

        fun setMessage(message: String) {
            this.message = message
        }

        fun setSeen(seen: Boolean) {
            this.isSeen = seen
        }

        fun setUrl(url: String) {
            this.url = url
        }

        fun setMsgId(msgId: String) {
            this.msgId = msgId
        }
    fun setRname(msgId: String) {
        this.msgId = msgId
    }
}