package com.example.effortkotlin.Model

class LevelGiaoTiep {
    private var Id: Int = 0
    private var Idlevel: Int = 0
    private var Titlelevel: String? = null
    private var Imagelevel: String? = null
    private var Levelstate: Int = 0

    constructor(id: Int, idlevel: Int, titlelevel: String, imagelevel: String, levelstate: Int){
        Id = id
        Idlevel = idlevel
        Titlelevel = titlelevel
        Imagelevel = imagelevel
        Levelstate = levelstate
    }

    fun LevelGiaoTiep() {}

    fun getId(): Int {
        return Id
    }

    fun setId(id: Int) {
        Id = id
    }

    fun getIdlevel(): Int {
        return Idlevel
    }

    fun setIdlevel(idlevel: Int) {
        Idlevel = idlevel
    }

    fun getTitlelevel(): String? {
        return Titlelevel
    }

    fun setTitlelevel(titlelevel: String) {
        Titlelevel = titlelevel
    }

    fun getImagelevel(): String? {
        return Imagelevel
    }

    fun setImagelevel(imagelevel: String) {
        Imagelevel = imagelevel
    }

    fun getLevelstate(): Int {
        return Levelstate
    }

    fun setLevelstate(levelstate: Int) {
        Levelstate = levelstate
    }




}