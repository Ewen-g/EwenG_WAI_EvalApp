package com.example.eweng_evalapp.ui

import android.content.Context
import android.content.SharedPreferences

class LocalPreferences private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

    fun saveStringValue(yourValue: String?) {
        sharedPreferences.edit().putString("saveStringValue", yourValue).apply()
    }

    fun getSaveStringValue(): String? {
        return sharedPreferences.getString("saveStringValue", null)
    }
    fun addToHistory(newEntry: String){
        val history = this.getHistory()             // on ajoute a une variable hystory toutes les données de l'ancien sharedpreferences
        history?.add(newEntry)                      // on ajoute à la variable les nouvelles données
        clear()                                     // efface toutes les données de l'ancien sharedpreferences
        sharedPreferences.edit().putStringSet("histories", history).apply() // on ajoute la variable precedente au nouveau sharedpreferences
    }

    fun getHistory(): MutableSet<String>? {
        return sharedPreferences.getStringSet("histories", HashSet<String>())
    }

    fun clear(){
        sharedPreferences.edit().remove("histories").apply()
    }

    companion object {
        private var INSTANCE: LocalPreferences? = null

        fun getInstance(context: Context): LocalPreferences {
            return INSTANCE?.let {
                INSTANCE
            } ?: run {
                INSTANCE = LocalPreferences(context)
                return INSTANCE!!
            }
        }
    }

}
