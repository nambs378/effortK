package com.example.effortkotlin.Model

class Settings {
    private var timeSet: Int = 0
    private var sw: Int = 0

    constructor(timeSet: Int, sw: Int) {
        this.timeSet = timeSet
        this.sw = sw
    }

    constructor(){}

    fun getTimeSet(): Int {
        return timeSet
    }

    fun setTimeSet(timeSet: Int) {
        this.timeSet = timeSet
    }

    fun getSw(): Int {
        return sw
    }

    fun setSw(sw: Int) {
        this.sw = sw
    }


}