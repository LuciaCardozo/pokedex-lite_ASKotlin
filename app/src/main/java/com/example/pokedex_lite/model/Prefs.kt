package com.example.pokedex_lite.model

import android.content.Context

class Prefs (val context:Context) {

    private val sharedName = "sharedName"
    private val userId = "userId"
    private val userName = "username"
    private val imgEvolution = "imgEvolution"
    private val nameEvolution = "nameEvolution"
    private val lvlEvolution = "lvlEvolution"
    private val ultimoId = "ultimoId"

    private val storage = context.getSharedPreferences(sharedName,0)

    fun saveUserId(id:String){
        storage.edit().putString(userId,id).apply()
    }

    fun getUserId():String{
        return storage.getString(userId,"")!!
    }

    fun saveUsername(username:String){
        storage.edit().putString(userName,username).apply()
    }

    fun getUsername():String{
        return storage.getString(userName,"")!!
    }

    fun getImgEvolution():String{
        return storage.getString(imgEvolution,"")!!
    }

    fun saveImgEvolution(imgPokemon:String){
        storage.edit().putString(imgEvolution,imgPokemon).apply()
    }
    fun getNameEvolution():String{
        return storage.getString(nameEvolution,"")!!
    }

    fun saveNameEvolution(imgPokemon:String){
        storage.edit().putString(nameEvolution,imgPokemon).apply()
    }
    fun getLvlEvolution():String{
        return storage.getString(lvlEvolution,"")!!
    }

    fun saveLvlEvolution(imgPokemon:String){
        storage.edit().putString(lvlEvolution,imgPokemon).apply()
    }

    fun saveUltimoId(id:Int){
        storage.edit().putInt(ultimoId,id).apply()
    }

    fun getUltimoId():Int{
        return storage.getInt(ultimoId,0)!!
    }

    fun wipe(){
        storage.edit().clear().apply()
    }
}