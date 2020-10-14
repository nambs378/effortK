package com.example.effortkotlin.Model

import java.io.Serializable

class TuVung : Serializable {
    private var Id: Int = 0
    private var Tu: String? = null
    private var Nghia: String? = null
    private var Idlevel: Int = 0
    private var Importants : Int? = 0

    constructor(id: Int, tu: String, nghia: String, idlevel: Int, importants: Int?){
        Id = id
        Tu = tu
        Nghia = nghia
        Idlevel = idlevel
        Importants = importants
    }

    constructor()


    fun getId(): Int {
        return Id
    }

    fun setId(id: Int) {
        Id = id
    }

    fun getTu(): String? {
        return Tu
    }

    fun setTu(tu: String) {
        Tu = tu
    }

    fun getNghia(): String? {
        return Nghia
    }

    fun setNghia(nghia: String) {
        Nghia = nghia
    }

    fun getIdlevel(): Int {
        return Idlevel
    }

    fun setIdlevel(idlevel: Int) {
        Idlevel = idlevel
    }

    fun getImportants(): Int? {
        return Importants
    }

    fun setImportants(importants: Int) {
        Importants = importants
    }


}