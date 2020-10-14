package com.example.effortkotlin.Model

class TransVoca {
    internal var vocat: String? = ""
    internal var mean: String? = ""
    internal var importants: Int = 0


    constructor(vocat: String, mean: String, importants: Int) {
        this.vocat = vocat
        this.mean = mean
        this.importants = importants
    }

    constructor()


}