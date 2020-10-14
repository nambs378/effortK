package com.example.effortkotlin.Model

class NotiVoca {
    private var vocat: String? = ""
    private var mean: String? =""
    private var importants: Int = 0

    constructor() {}

    constructor(vocat: String, mean: String, importants: Int) {
        this.vocat = vocat
        this.mean = mean
        this.importants = importants
    }

    fun getVocat(): String? {
        return vocat
    }

    fun setVocat(vocat: String) {
        this.vocat = vocat
    }

    fun getMean(): String? {
        return mean
    }

    fun setMean(mean: String) {
        this.mean = mean
    }

    fun getImportants(): Int {
        return importants
    }

    fun setImportants(importants: Int) {
        this.importants = importants
    }

}