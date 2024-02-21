package com.example.pet.ModelClasses

import java.io.Serializable

class Community (
    private var name: String = "",
    private var profile: String = "",
    private var id: String = "",
    ): Serializable
{
    fun getId():String {
        return id
    }
    fun getName():String {
        return name
    }
    fun getProfile():String {
        return profile
    }
}